package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.BankDTO;
import lk.ijse.gdse71.backend.service.BankService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bank")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class BankController {
    private final BankService bankService;

    @PostMapping("/save")
    public ResponseEntity<APIResponse> saveBank(@RequestBody BankDTO bankDTO) {
        log.info("Saving bank: {}", bankDTO.getName());
        bankService.save(bankDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse(201, "Bank saved successfully", true));
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateBank(@RequestBody BankDTO bankDTO) {
        log.info("Updating bank id={}", bankDTO.getId());
        bankService.update(bankDTO);
        return ResponseEntity.ok(new APIResponse(200, "Bank updated successfully", true));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deleteBank(@PathVariable Long id) {
        log.info("Deleting bank id={}", id);
        bankService.delete(id);
        return ResponseEntity.ok(new APIResponse(200, "Bank deleted successfully", true));
    }

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse> getAllBanks() {
        log.info("Fetching all banks");
        List<BankDTO> banks = bankService.getAllBanks();
        return ResponseEntity.ok(new APIResponse(200, "Banks retrieved successfully", banks));
    }

}
