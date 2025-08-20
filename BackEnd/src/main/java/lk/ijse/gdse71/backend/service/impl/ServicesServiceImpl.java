package lk.ijse.gdse71.backend.service.impl;

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
}
