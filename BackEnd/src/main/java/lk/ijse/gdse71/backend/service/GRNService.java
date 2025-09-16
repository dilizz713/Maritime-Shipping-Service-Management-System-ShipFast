package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.GRNDTO;


public interface GRNService {

    GRNDTO getGrnByBillNumber(String billNumber);

    void updateGRN(GRNDTO dto);


}
