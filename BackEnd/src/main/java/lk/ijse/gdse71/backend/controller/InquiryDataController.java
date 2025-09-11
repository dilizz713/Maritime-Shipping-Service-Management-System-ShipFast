package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ProductDTO;
import lk.ijse.gdse71.backend.dto.VendorDTO;
import lk.ijse.gdse71.backend.repo.ProductRepository;
import lk.ijse.gdse71.backend.repo.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/data")
@RequiredArgsConstructor
@CrossOrigin
public class InquiryDataController {
    private final VendorRepository vendorRepo;
    private final ProductRepository productRepo;

    @GetMapping("/vendors")
    public List<VendorDTO> getAllVendors() {
        return vendorRepo.findAll().stream()
                .map(v -> new VendorDTO(v.getId(), v.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        return productRepo.findAll().stream()
                .map(p -> new ProductDTO(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }
}
