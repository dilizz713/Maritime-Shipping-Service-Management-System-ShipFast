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
    public void update(BankAccountDTO dto) {
        BankAccount account = bankAccountRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        account.setAccountNumber(dto.getAccountNumber());
        account.setBranch(dto.getBranch());
        account.setCurrency(dto.getCurrency());

        // Fetch vendor
        Vendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        account.setVendor(vendor);

        // Handle Bank: either by ID or create/find by name
        Bank bank = null;
        if (dto.getBankId() != null) {
            bank = bankRepository.findById(dto.getBankId())
                    .orElseThrow(() -> new RuntimeException("Bank not found"));
        } else if (dto.getBankName() != null && !dto.getBankName().isEmpty()) {
            bank = bankRepository.findByName(dto.getBankName())
                    .orElseGet(() -> bankRepository.save(Bank.builder()
                            .name(dto.getBankName())
                            .swiftCode(null)
                            .build()));
        } else {
            throw new RuntimeException("Bank information is missing");
        }

        account.setBank(bank);
        bankAccountRepository.save(account);
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

    @Override
    public List<BankAccountDTO> getAccountsByVendor(Long vendorId) {
        List<BankAccount> accounts = bankAccountRepository.findByVendorId(vendorId);
        return accounts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private BankAccountDTO toDTO(BankAccount account) {
        return new BankAccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getBranch(),
                account.getCurrency(),
                account.getVendor().getId(),
                account.getBank().getId(),
                account.getBank().getName()
        );
    }

    @Override
    public BankAccountDTO getBankAccountById(Long id) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        return toDTO(account);
    }
}
