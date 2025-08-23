package lk.ijse.gdse71.backend.service.impl;


import lk.ijse.gdse71.backend.dto.PortDTO;
import lk.ijse.gdse71.backend.dto.ServiceDTO;
import lk.ijse.gdse71.backend.dto.ServiceRequestDTO;
import lk.ijse.gdse71.backend.entity.*;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.CustomerRepository;
import lk.ijse.gdse71.backend.repo.PortRepository;
import lk.ijse.gdse71.backend.repo.RequestServiceRepository;
import lk.ijse.gdse71.backend.repo.ServiceRepository;
import lk.ijse.gdse71.backend.service.RequestServicesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        serviceRequest.setShipName(dto.getShipName());

        serviceRequest.setStatus(RequestStatus.PENDING);

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

    @Override
    public void updateRequest(ServiceRequestDTO dto) {
        ServiceRequest existingRequest = requestServicesRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found"));

        if (dto.getShipName() != null) {
            existingRequest.setShipName(dto.getShipName());
        }
        if (dto.getDescription() != null) {
            existingRequest.setDescription(dto.getDescription());
        }

        // ---- Update Customer ----
        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            existingRequest.setCustomer(customer);
        }

        // ---- Update Services ----
        if (dto.getServiceIds() != null && !dto.getServiceIds().isEmpty()) {
            List<Services> services = servicesRepository.findAllById(dto.getServiceIds());
            if (services.isEmpty()) {
                throw new ResourceNotFoundException("No valid services found for provided IDs");
            }
            existingRequest.setServices(services);
        }

        // ---- Update Port ----
        if (dto.getPortId() != null) {
            Port port = portRepository.findById(dto.getPortId())
                    .orElseThrow(() -> new ResourceNotFoundException("Port not found"));
            existingRequest.setPort(port);
        }

        requestServicesRepository.save(existingRequest);
    }


   /* @Override
    public List<ServiceRequestDTO> getAllRequestsByCustomer(Long customerId) {
        List<ServiceRequest> requests = requestServicesRepository.findByCustomerId(customerId);

        return requests.stream().map(request -> {
            ServiceRequestDTO dto = new ServiceRequestDTO();
            dto.setId(request.getId());
            dto.setShipName(request.getShipName());
            dto.setRequestingDate(request.getRequestingDate());
            dto.setCustomerId(request.getCustomer().getId());
            dto.setPortId(request.getPort().getId());
            dto.setDescription(request.getDescription());
            dto.setStatus(request.getStatus().name());


            dto.setServiceIds(
                    request.getServices()
                            .stream()
                            .map(Services::getId)
                            .toList()
            );

            return dto;
        }).toList();
    }*/

    @Override
    public List<ServiceRequestDTO> getAllRequestsByCustomer(Long customerId) {
        List<ServiceRequest> requests = requestServicesRepository.findByCustomerId(customerId);

        return requests.stream().map(req -> {
            ServiceRequestDTO dto = new ServiceRequestDTO();
            dto.setId(req.getId());
            dto.setShipName(req.getShipName());
            dto.setRequestingDate(req.getRequestingDate());
            dto.setCustomerId(req.getCustomer().getId());
            dto.setDescription(req.getDescription());
            dto.setStatus(req.getStatus().name());
            dto.setPortId(req.getPort() != null ? req.getPort().getId() : null);
            dto.setPortName(req.getPort() != null ? req.getPort().getPortName() : "N/A");
            dto.setServiceIds(req.getServices() != null
                    ? req.getServices().stream().map(Services::getId).collect(Collectors.toList())
                    : List.of());
            dto.setServiceNames(req.getServices() != null
                    ? req.getServices().stream().map(Services::getServiceName).collect(Collectors.toList())
                    : List.of());
            return dto;
        }).collect(Collectors.toList());
    }





}
