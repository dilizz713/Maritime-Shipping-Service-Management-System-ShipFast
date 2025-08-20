package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.PortDTO;
import lk.ijse.gdse71.backend.entity.Port;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.repo.PortRepository;
import lk.ijse.gdse71.backend.service.PortService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
