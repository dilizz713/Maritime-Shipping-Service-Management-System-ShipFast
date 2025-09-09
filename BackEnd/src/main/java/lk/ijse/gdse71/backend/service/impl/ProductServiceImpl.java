package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.ProductDTO;
import lk.ijse.gdse71.backend.entity.Product;
import lk.ijse.gdse71.backend.entity.ProductType;
import lk.ijse.gdse71.backend.entity.UOM;
import lk.ijse.gdse71.backend.repo.ProductRepository;
import lk.ijse.gdse71.backend.repo.UOMRepository;
import lk.ijse.gdse71.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UOMRepository uomRepository;

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        // 1. Check if UOM exists
        UOM uom = null;

        if (productDTO.getUomId() != null) {
            // Load existing UOM by ID
            uom = uomRepository.findById(productDTO.getUomId())
                    .orElseThrow(() -> new RuntimeException("UOM not found with id: " + productDTO.getUomId()));
        } else if (productDTO.getUomName() != null && productDTO.getUomCode() != null) {
            // Try to find by name, otherwise create
            uom = uomRepository.findByUomNameIgnoreCase(productDTO.getUomName())
                    .orElseGet(() -> uomRepository.save(
                            UOM.builder()
                                    .uomName(productDTO.getUomName())
                                    .uomCode(productDTO.getUomCode())
                                    .build()
                    ));
        }

        // 2. Save product with selected/created UOM
        Product product = Product.builder()
                .code(productDTO.getCode())
                .name(productDTO.getName())
                .productType(productDTO.getProductType() != null
                        ? ProductType.valueOf(productDTO.getProductType())
                        : null)
                .quantity(productDTO.getQuantity())
                .unitPrice(productDTO.getUnitPrice())
                .uom(uom)
                .build();

        Product savedProduct = productRepository.save(product);

        // 3. Convert back to DTO
        return toDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getProductType() != null ? product.getProductType().name() : null,
                product.getQuantity(),
                product.getUnitPrice(),
                product.getUom().getId(),
                product.getUom().getUomName(),
                product.getUom().getUomCode()
        );
    }
}
