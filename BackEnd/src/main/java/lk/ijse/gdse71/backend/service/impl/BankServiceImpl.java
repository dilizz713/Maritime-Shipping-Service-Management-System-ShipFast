package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.BankDTO;
import lk.ijse.gdse71.backend.entity.Bank;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.BankRepository;
import lk.ijse.gdse71.backend.service.BankService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(BankDTO bankDTO) {
        if (bankRepository.existsByName(bankDTO.getName()) || bankRepository.existsBySwiftCode(bankDTO.getSwiftCode())) {
            throw new ResourceAlreadyExists("Bank with same name or SWIFT code already exists!");
        }
        Bank bank = modelMapper.map(bankDTO, Bank.class);
        bankRepository.save(bank);
    }

    @Override
    public void update(BankDTO bankDTO) {
        Bank existing = bankRepository.findById(bankDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        existing.setName(bankDTO.getName());
        existing.setSwiftCode(bankDTO.getSwiftCode());
        bankRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Bank existing = bankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        bankRepository.delete(existing);
    }

    @Override
    public List<BankDTO> getAllBanks() {
        return bankRepository.findAll()
                .stream()
                .map(bank -> modelMapper.map(bank, BankDTO.class))
                .collect(Collectors.toList());
    }
}
