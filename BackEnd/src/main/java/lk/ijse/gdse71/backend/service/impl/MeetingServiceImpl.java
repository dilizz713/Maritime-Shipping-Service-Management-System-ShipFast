package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.MeetingDTO;
import lk.ijse.gdse71.backend.entity.Meeting;
import lk.ijse.gdse71.backend.repo.MeetingRepository;
import lk.ijse.gdse71.backend.service.MeetingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    @Override
    public Meeting createMeeting(MeetingDTO dto) {
        String code = generateCode();
        Meeting m = Meeting.builder()
                .code(code)
                .topic(dto.getTopic())
                .createdAt(LocalDateTime.now())
                .createdByEmployeeId(dto.getCreatedByEmployeeId())
                .build();
        return meetingRepository.save(m);
    }

    @Override
    public Meeting getByCode(String code) {
        return meetingRepository.findByCode(code).orElse(null);
    }

    private String generateCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase();
    }
}