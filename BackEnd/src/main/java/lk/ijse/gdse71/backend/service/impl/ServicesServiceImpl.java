package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.ServiceDTO;
import lk.ijse.gdse71.backend.entity.Services;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.repo.ServiceRepository;
import lk.ijse.gdse71.backend.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(ServiceDTO serviceDTO) {
       Services service = serviceRepository.findByServiceName(serviceDTO.getServiceName());
       if(service == null) {
           serviceRepository.save(modelMapper.map(serviceDTO, Services.class));
       }else {
           throw new ResourceAlreadyExists("Service is already exists");
       }
    }
}
