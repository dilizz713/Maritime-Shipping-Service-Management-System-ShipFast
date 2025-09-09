package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.BankAccountDTO;
import lk.ijse.gdse71.backend.entity.Bank;
import lk.ijse.gdse71.backend.entity.BankAccount;
import lk.ijse.gdse71.backend.entity.Vendor;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.BankAccountRepository;
import lk.ijse.gdse71.backend.repo.BankRepository;
import lk.ijse.gdse71.backend.repo.VendorRepository;
import lk.ijse.gdse71.backend.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final VendorRepository vendorRepository;
    private final BankRepository bankRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(BankAccountDTO bankAccountDTO) {
        BankAccount existing = bankAccountRepository.findByAccountNumber(bankAccountDTO.getAccountNumber());
        if (existing != null) {
            throw new ResourceAlreadyExists("This bank account already exists!");
        }

        BankAccount account = new BankAccount();
        account.setAccountNumber(bankAccountDTO.getAccountNumber());
        account.setBranch(bankAccountDTO.getBranch());
        account.setCurrency(bankAccountDTO.getCurrency());

        Vendor vendor = vendorRepository.findById(bankAccountDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        account.setVendor(vendor);

        Bank bank = bankRepository.findById(bankAccountDTO.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        account.setBank(bank);

        bankAccountRepository.save(account);
    }

    @Override
    public void update(BankAccountDTO bankAccountDTO) {
        BankAccount existingAccount = bankAccountRepository.findById(bankAccountDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));

        existingAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
        existingAccount.setBranch(bankAccountDTO.getBranch());
        existingAccount.setCurrency(bankAccountDTO.getCurrency());

        Vendor vendor = vendorRepository.findById(bankAccountDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        existingAccount.setVendor(vendor);

        Bank bank = bankRepository.findById(bankAccountDTO.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        existingAccount.setBank(bank);

        bankAccountRepository.save(existingAccount);
    }

    @Override
    public void delete(Long id) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));
        bankAccountRepository.delete(account);
    }

    @Override
    public List<BankAccountDTO> getAllAccounts() {
        return bankAccountRepository.findAll()
                .stream()
                .map(account -> {
                    BankAccountDTO dto = new BankAccountDTO();
                    dto.setId(account.getId());
                    dto.setAccountNumber(account.getAccountNumber());
                    dto.setBranch(account.getBranch());
                    dto.setCurrency(account.getCurrency());
                    dto.setVendorId(account.getVendor().getId());
                    dto.setBankId(account.getBank().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
