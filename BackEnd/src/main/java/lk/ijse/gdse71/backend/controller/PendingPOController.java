package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.PendingPODTO;
import lk.ijse.gdse71.backend.service.PendingPOService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/pending-po")
@RequiredArgsConstructor
@CrossOrigin
public class PendingPOController {
    private final PendingPOService pendingPOService;

    @GetMapping("/getAll")
    public List<PendingPODTO> getAllPendingPOs() {
        return pendingPOService.getAllPendingPOs();
    }
}
