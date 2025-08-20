package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.PortDTO;
import lk.ijse.gdse71.backend.service.PortService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/port")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class PortController {
    private final PortService portService;

    @PostMapping("/savePort")
    public ResponseEntity<APIResponse> savePort(@RequestBody PortDTO portDTO) {
        portService.save(portDTO);
        return ResponseEntity.ok(new APIResponse(201 , "Port Saved Successfully", true));
    }
}
