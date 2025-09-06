package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.PortDTO;
import lk.ijse.gdse71.backend.dto.ServiceDTO;
import lk.ijse.gdse71.backend.entity.Services;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.ServiceRepository;
import lk.ijse.gdse71.backend.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ServiceDTO> getAllServices() {
        List<Services> services = serviceRepository.findAll();
        if(services.isEmpty()) {
            throw new ResourceNotFoundException("No services found");
        }
        return modelMapper.map(services, new TypeToken<List<ServiceDTO>>(){}.getType());
    }

    @Override
    public void update(ServiceDTO serviceDTO) {
        Optional<Services> services = serviceRepository.findById(serviceDTO.getId());
        if(services.isPresent()) {
            Services existingServices = services.get();
            existingServices.setServiceName(serviceDTO.getServiceName());
            existingServices.setDescription(serviceDTO.getDescription());

            serviceRepository.save(existingServices);
        }else{
            throw new ResourceNotFoundException("This Service does not exist");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Services> services = serviceRepository.findById(id);
        if(services.isPresent()) {
            serviceRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("This Service does not exist");
        }
    }
}
