package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.BankDTO;

import java.util.List;

public interface BankService {
    void save(BankDTO bankDTO);

    void update(BankDTO bankDTO);

    void delete(Long id);

    List<BankDTO> getAllBanks();
}
