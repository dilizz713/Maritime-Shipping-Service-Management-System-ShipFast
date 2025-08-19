package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void save(CustomerDTO customerDTO);

    void update(CustomerDTO customerDTO);

    void delete(Long id);

    List<CustomerDTO> getAllCustomers();
}
