package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.PendingPODTO;

import java.util.List;

public interface PendingPOService {

    List<PendingPODTO> getAllPendingPOs();
}
