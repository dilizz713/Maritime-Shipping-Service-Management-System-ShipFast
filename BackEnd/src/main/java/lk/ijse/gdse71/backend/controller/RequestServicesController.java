package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ServiceRequestDTO;
import lk.ijse.gdse71.backend.service.RequestServicesService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/requestServices")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class RequestServicesController {
    private final RequestServicesService requestServicesService;

    @PostMapping("/saveRequest")
    public ResponseEntity<APIResponse> saveRequest(@RequestBody ServiceRequestDTO serviceRequestDTO) {
        requestServicesService.saveRequest(serviceRequestDTO);
        return ResponseEntity.ok(new APIResponse(201,"Request saved successfully",true));
    }

}
