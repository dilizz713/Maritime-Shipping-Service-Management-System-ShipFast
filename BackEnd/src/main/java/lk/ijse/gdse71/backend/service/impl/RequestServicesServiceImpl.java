package lk.ijse.gdse71.backend.service.impl;


import lk.ijse.gdse71.backend.dto.ServiceRequestDTO;
import lk.ijse.gdse71.backend.entity.Customer;
import lk.ijse.gdse71.backend.entity.Port;
import lk.ijse.gdse71.backend.entity.ServiceRequest;
import lk.ijse.gdse71.backend.entity.Services;
import lk.ijse.gdse71.backend.repo.CustomerRepository;
import lk.ijse.gdse71.backend.repo.PortRepository;
import lk.ijse.gdse71.backend.repo.RequestServiceRepository;
import lk.ijse.gdse71.backend.repo.ServiceRepository;
import lk.ijse.gdse71.backend.service.RequestServicesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class RequestServicesServiceImpl implements RequestServicesService {
    private final RequestServiceRepository requestServicesRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRepository servicesRepository;
    private final PortRepository portRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveRequest(ServiceRequestDTO dto) {
        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setRequestingDate(new Date());
        serviceRequest.setDescription(dto.getDescription());

        // ---- Customer ----
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        serviceRequest.setCustomer(customer);

        // ---- Services ----
        List<Services> services = servicesRepository.findAllById(dto.getServiceIds());
        serviceRequest.setServices(services);

        // ---- Port  ----
        Port port = portRepository.findById(dto.getPortId())
                .orElseThrow(() -> new RuntimeException("Port not found"));
        serviceRequest.setPort(port);

        requestServicesRepository.save(serviceRequest);
    }
}
