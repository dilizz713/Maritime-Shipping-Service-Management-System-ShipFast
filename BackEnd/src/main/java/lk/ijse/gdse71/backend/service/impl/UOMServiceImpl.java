package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.PortDTO;
import lk.ijse.gdse71.backend.dto.UOMDTO;
import lk.ijse.gdse71.backend.entity.UOM;
import lk.ijse.gdse71.backend.repo.UOMRepository;
import lk.ijse.gdse71.backend.service.UOMService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UOMServiceImpl implements UOMService {
    private final UOMRepository uomRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<UOMDTO> getAllUOMs() {
        List<UOM> uoms = uomRepository.findAll();
        if (uoms.isEmpty()) {
            throw new RuntimeException("No UOMs found");
        } else {
            return modelMapper.map(uoms, new TypeToken<List<UOMDTO>>() {
            }.getType());
        }
    }
}
