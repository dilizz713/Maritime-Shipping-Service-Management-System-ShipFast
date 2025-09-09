package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.BankAccountDTO;

import java.util.List;

public interface BankAccountService {
    void save(BankAccountDTO bankAccountDTO);

    void update(BankAccountDTO bankAccountDTO);

    void delete(Long id);


    List<BankAccountDTO> getAllAccounts();

    List<BankAccountDTO> getAccountsByVendor(Long vendorId);

    BankAccountDTO getBankAccountById(Long id);
}
