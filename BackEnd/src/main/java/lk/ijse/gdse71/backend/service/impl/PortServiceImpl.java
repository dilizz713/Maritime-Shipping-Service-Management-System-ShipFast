package lk.ijse.gdse71.backend.service.impl;

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
}
