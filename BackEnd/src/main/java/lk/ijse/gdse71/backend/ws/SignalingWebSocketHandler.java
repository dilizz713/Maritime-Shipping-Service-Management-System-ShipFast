package lk.ijse.gdse71.backend.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SignalingWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();
    private final Map<String, String> sessionMeeting = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        removeFromRoom(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        removeFromRoom(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode node = mapper.readTree(message.getPayload());
        String type = node.get("type").asText();
        String meetingCode = node.get("meetingCode").asText();

        if(session.getAttributes().get("employeeName") != null) {
            ((ObjectNode) node).put("employeeName", (String) session.getAttributes().get("employeeName"));
        }

        if ("join".equals(type)) {
            rooms.computeIfAbsent(meetingCode, k -> ConcurrentHashMap.newKeySet()).add(session);
            sessionMeeting.put(session.getId(), meetingCode);
            broadcastToRoom(meetingCode, node.toString(), session);
            return;
        }

        if ("leave".equals(type)) {
            removeFromRoom(session);
            broadcastToRoom(meetingCode, node.toString(), session);
            return;
        }

        broadcastToRoom(meetingCode, node.toString(), session);
    }

    private void broadcastToRoom(String meetingCode, String payload, WebSocketSession origin) {
        Set<WebSocketSession> sessions = rooms.get(meetingCode);
        if (sessions == null) return;
        for (WebSocketSession s : sessions) {
            try {
                if (s.isOpen() && !s.getId().equals(origin.getId())) {
                    s.sendMessage(new TextMessage(payload));
                }
            } catch (Exception e) {
                log.error("Failed to send WS message", e);
            }
        }
    }

    private void removeFromRoom(WebSocketSession session) {
        String code = sessionMeeting.remove(session.getId());
        if (code == null) return;
        Set<WebSocketSession> set = rooms.get(code);
        if (set != null) {
            set.remove(session);
            if (set.isEmpty()) rooms.remove(code);
        }
    }
}