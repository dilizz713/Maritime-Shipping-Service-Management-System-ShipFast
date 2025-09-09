package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.VendorDTO;

import java.util.List;

public interface VendorService {
    void save(VendorDTO vendorDTO);

    void update(VendorDTO vendorDTO);

    void delete(Long id);

    List<VendorDTO> getAllVendors();
}
