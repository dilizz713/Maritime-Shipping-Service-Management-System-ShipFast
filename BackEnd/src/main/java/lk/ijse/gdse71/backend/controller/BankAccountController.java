package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.BankAccountDTO;
import lk.ijse.gdse71.backend.service.BankAccountService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bankAccount")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/saveBankDetails")
    public ResponseEntity<APIResponse> saveBankDetails(@RequestBody BankAccountDTO bankAccountDTO) {
        log.info("Saving bank account details for vendorId={}", bankAccountDTO.getVendorId());
        bankAccountService.save(bankAccountDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse(201, "Bank Details saved successfully", true));
    }

    @PutMapping("/updateBankDetails")
    public ResponseEntity<APIResponse> updateBankDetails(@RequestBody BankAccountDTO bankAccountDTO) {
        log.info("Updating bank account with id={}", bankAccountDTO.getId());
        bankAccountService.update(bankAccountDTO);
        return ResponseEntity.ok(new APIResponse(200, "Bank Details updated successfully", true));
    }

    @DeleteMapping("/removeAccount/{id}")
    public ResponseEntity<APIResponse> removeBankAccount(@PathVariable Long id) {
        log.info("Removing bank account with id={}", id);
        bankAccountService.delete(id);
        return ResponseEntity.ok(new APIResponse(200, "Bank Account removed successfully", true));
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<APIResponse> getAllAccounts() {
        log.info("Fetching all bank accounts");
        List<BankAccountDTO> bankAccountDTOs = bankAccountService.getAllAccounts();
        return ResponseEntity.ok(new APIResponse(200, "Bank Accounts retrieved successfully", bankAccountDTOs));
    }
}
