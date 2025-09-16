package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.GRNDTO;
import lk.ijse.gdse71.backend.dto.GRNItemDTO;

import java.util.List;

public interface GRNService {
    GRNDTO getGRNByBillNumber(String billNumber);

    GRNDTO createGRNFromVerification(Long confirmId);
}
