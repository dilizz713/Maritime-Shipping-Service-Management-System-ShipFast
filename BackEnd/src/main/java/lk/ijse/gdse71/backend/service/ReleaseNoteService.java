package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.ReleaseNoteDTO;

import java.util.List;

public interface ReleaseNoteService {
    void saveReleaseNotes(List<ReleaseNoteDTO> notes);
}
