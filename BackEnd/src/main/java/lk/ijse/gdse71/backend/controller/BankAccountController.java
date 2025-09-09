package lk.ijse.gdse71.backend.controller;


import lk.ijse.gdse71.backend.dto.BankAccountDTO;
import lk.ijse.gdse71.backend.dto.VendorDTO;
import lk.ijse.gdse71.backend.service.BankAccountService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        bankAccountService.save(bankAccountDTO);
        return  ResponseEntity.ok(new APIResponse(201 , "Bank Details saved successfully" , true));
    }

    @PutMapping("/updateBankDetails")
    public ResponseEntity<APIResponse> updateBankDetails(@RequestBody BankAccountDTO bankAccountDTO) {
        bankAccountService.update(bankAccountDTO);
        return ResponseEntity.ok(new APIResponse(200 , "Bank Details updated successfully" , true));

    }

    @DeleteMapping("/removeAccount/{id}")
    public ResponseEntity<APIResponse> removeBankAccount(@PathVariable Long id) {
        bankAccountService.delete(id);
        return ResponseEntity.ok(new APIResponse(200 , "Bank Account Removed successfully" , true));

    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<APIResponse> getAllAccounts() {
        List<BankAccountDTO> bankAccountDTO = bankAccountService.getAllVendors();
        return ResponseEntity.ok(new APIResponse(200 , "Bank Accounts retrieved successfully" , bankAccountDTO));
    }
}
