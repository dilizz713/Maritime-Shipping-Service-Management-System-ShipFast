package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.VesselsDTO;
import lk.ijse.gdse71.backend.service.VesselService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vessels")
@RequiredArgsConstructor
@CrossOrigin
public class VesselsController {
    private final VesselService vesselsService;

    @PostMapping("/saveVessel")
    public ResponseEntity<APIResponse> saveVessel(@RequestBody VesselsDTO dto) {
        vesselsService.saveVessel(dto);
        return ResponseEntity.ok(new APIResponse(201, "Vessel saved successfully!", true));
    }

    @PutMapping("/updateVessel")
    public ResponseEntity<APIResponse> updateVessel(@RequestBody VesselsDTO dto) {
        vesselsService.updateVessel(dto.getId(), dto);
        return ResponseEntity.ok(new APIResponse(200, "Vessel updated successfully!", true));
    }

    @DeleteMapping("/deleteVessel/{id}")
    public ResponseEntity<APIResponse> deleteVessel(@PathVariable Long id) {
        vesselsService.deleteVessel(id);
        return ResponseEntity.ok(new APIResponse(200, "Vessel deleted successfully!", true));
    }

    @GetMapping("/getAllVessels")
    public ResponseEntity<APIResponse> getAllVessels() {
        List<VesselsDTO> vesselList = vesselsService.getAllVessels();
        return ResponseEntity.ok(new APIResponse(200, "Vessel list retrieved successfully", vesselList));
    }

    @GetMapping("/getVessel/{id}")
    public ResponseEntity<APIResponse> getVesselById(@PathVariable Long id) {
        VesselsDTO vessel = vesselsService.getVesselById(id);
        return ResponseEntity.ok(new APIResponse(200, "Vessel retrieved successfully", vessel));
    }


}
