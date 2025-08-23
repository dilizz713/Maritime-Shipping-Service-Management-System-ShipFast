package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.CustomerDTO;
import lk.ijse.gdse71.backend.entity.Customer;
import lk.ijse.gdse71.backend.entity.CustomerType;
import lk.ijse.gdse71.backend.entity.User;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.CustomerRepository;
import lk.ijse.gdse71.backend.repo.UserRepository;
import lk.ijse.gdse71.backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public void save(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByEmailAndCompanyName(customerDTO.getEmail(), customerDTO.getCompanyName());

        if (customer == null) {
            Customer newCustomer = modelMapper.map(customerDTO, Customer.class);

            if (customerDTO.getUserId() != null) {
                User user = userRepository.findById(customerDTO.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                newCustomer.setUser(user);
            }

            customerRepository.save(newCustomer);
        } else {
            throw new ResourceAlreadyExists("Already Exists");
        }
    }

    @Override
    public void update(CustomerDTO customerDTO) {
        Optional<Customer> customer = customerRepository.findById(customerDTO.getId());
        if(customer.isPresent()) {
            Customer existingCustomer = customer.get();
            existingCustomer.setCompanyName(customerDTO.getCompanyName());
            existingCustomer.setContactPerson(customerDTO.getContactPerson());
            existingCustomer.setAddress(customerDTO.getAddress());
            existingCustomer.setMobileNumber(customerDTO.getMobileNumber());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setCustomerType(CustomerType.valueOf(customerDTO.getCustomerType()));

            customerRepository.save(existingCustomer);
        }else{
            throw new ResourceNotFoundException("This Customer does not exist");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            customerRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("This Customer does not exist");
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if(customers.isEmpty()) {
            throw new ResourceNotFoundException("Customers not found");
        }
        return modelMapper.map(customers, new TypeToken<List<CustomerDTO>>(){}.getType());
    }


}
