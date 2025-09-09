package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.VendorDTO;
import lk.ijse.gdse71.backend.entity.Employee;
import lk.ijse.gdse71.backend.entity.Vendor;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.VendorRepository;
import lk.ijse.gdse71.backend.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.findByEmail(vendorDTO.getEmail());
        if(vendor == null) {
            vendorRepository.save(modelMapper.map(vendorDTO, Vendor.class));
        }else{
            throw new ResourceAlreadyExists("This vendor has already exists!");
        }
    }

    @Override
    public void update(VendorDTO vendorDTO) {
        Optional<Vendor> vendor = vendorRepository.findById(vendorDTO.getId());
        if(vendor.isPresent()) {
            Vendor existingVendor = vendor.get();
            existingVendor.setName(vendorDTO.getName());
            existingVendor.setAddress(vendorDTO.getAddress());
            existingVendor.setMobile(vendorDTO.getMobile());
            existingVendor.setEmail(vendorDTO.getEmail());
            existingVendor.setFax(vendorDTO.getFax());

            vendorRepository.save(existingVendor);
        }else{
            throw new ResourceAlreadyExists("This vendor does not exist");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if(vendor.isPresent()) {
            vendorRepository.deleteById(id);
        }else{
            throw new ResourceAlreadyExists("This vendor does not exist");
        }
    }

    @Override
    public List<VendorDTO> getAllVendors() {
       List<Vendor> vendors = vendorRepository.findAll();
       if(vendors.isEmpty()){
           throw new ResourceNotFoundException("No Vendors found");
       }else{
           return modelMapper.map(vendors, new TypeToken<List<VendorDTO>>(){}.getType());
       }
    }
}
