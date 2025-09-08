package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.MeetingDTO;
import lk.ijse.gdse71.backend.entity.Meeting;

public interface MeetingService {
    Meeting createMeeting(MeetingDTO dto);
    Meeting getByCode(String code);
}