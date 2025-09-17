package lk.ijse.gdse71.backend.service;

import ch.qos.logback.core.spi.ConfigurationEvent;
import lk.ijse.gdse71.backend.dto.VesselsDTO;

import java.util.List;

public interface VesselService {
    void saveVessel(VesselsDTO dto);

    void updateVessel(Long id, VesselsDTO dto);

    void deleteVessel(Long id);


    List<VesselsDTO> getAllVessels();

    VesselsDTO getVesselById(Long id);
}
