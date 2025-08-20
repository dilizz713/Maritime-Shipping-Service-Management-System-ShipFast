package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.CustomerDTO;
import lk.ijse.gdse71.backend.dto.PortDTO;
import lk.ijse.gdse71.backend.entity.Port;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.PortRepository;
import lk.ijse.gdse71.backend.service.PortService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortServiceImpl implements PortService {
    private final PortRepository portRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(PortDTO portDTO) {
        Port port = portRepository.findByPortName(portDTO.getPortName());
        if(port == null) {
            portRepository.save(modelMapper.map(portDTO, Port.class));
        }else{
            throw new ResourceAlreadyExists(portDTO.getPortName() + " port already exists");
        }
    }

    @Override
    public List<PortDTO> getAllPorts() {
        List<Port> ports = portRepository.findAll();
        if(ports.isEmpty()) {
            throw new ResourceNotFoundException("No Ports found");
        }
        return modelMapper.map(ports, new TypeToken<List<PortDTO>>(){}.getType());
    }
}
