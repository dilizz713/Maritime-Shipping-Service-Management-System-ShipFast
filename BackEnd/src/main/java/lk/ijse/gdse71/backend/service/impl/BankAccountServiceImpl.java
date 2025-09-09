package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.BankAccountDTO;
import lk.ijse.gdse71.backend.entity.BankAccount;
import lk.ijse.gdse71.backend.entity.Vendor;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.BankAccountRepository;
import lk.ijse.gdse71.backend.repo.VendorRepository;
import lk.ijse.gdse71.backend.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(BankAccountDTO bankAccountDTO) {
        BankAccount existing = bankAccountRepository.findByAccountNumber(bankAccountDTO.getAccountNumber());
        if (existing == null) {
            BankAccount account = modelMapper.map(bankAccountDTO, BankAccount.class);

            Vendor vendor = vendorRepository.findById(bankAccountDTO.getVendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
            account.setVendor(vendor);

            bankAccountRepository.save(account);
        } else {
            throw new ResourceAlreadyExists("This bank account already exists!");
        }
    }

    @Override
    public void update(BankAccountDTO bankAccountDTO) {
        Optional<BankAccount> account = bankAccountRepository.findById(bankAccountDTO.getId());
        if(account.isPresent()) {
            BankAccount existingAccount = account.get();
            existingAccount.setBank(bankAccountDTO.getBank());
            existingAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
            existingAccount.setBranch(bankAccountDTO.getBranch());
            existingAccount.setSwiftCode(bankAccountDTO.getSwiftCode());
            existingAccount.setCurrency(bankAccountDTO.getCurrency());

            Vendor vendor = vendorRepository.findById(bankAccountDTO.getVendorId())
                    .orElseThrow(() -> new RuntimeException("Vendor not found"));
            existingAccount.setVendor(vendor);

            bankAccountRepository.save(existingAccount);
        }else{
            throw new ResourceAlreadyExists("This bank account does not exist");
        }

    }

    @Override
    public void delete(Long id) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank account not found"));
        bankAccountRepository.delete(account);
    }

    @Override
    public List<BankAccountDTO> getAllVendors() {
        return bankAccountRepository.findAll()
                .stream()
                .map(account -> {
                    BankAccountDTO dto = modelMapper.map(account, BankAccountDTO.class);
                    dto.setVendorId(account.getVendor().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
