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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UOMRepository uomRepository;

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        // Handle UOM
        UOM uom = null;
        if (productDTO.getUomId() != null) {
            uom = uomRepository.findById(productDTO.getUomId())
                    .orElseThrow(() -> new RuntimeException("UOM not found with id: " + productDTO.getUomId()));
        } else if (productDTO.getUomName() != null && productDTO.getUomCode() != null) {
            uom = uomRepository.findByUomNameIgnoreCase(productDTO.getUomName())
                    .orElseGet(() -> uomRepository.save(UOM.builder()
                            .uomName(productDTO.getUomName())
                            .uomCode(productDTO.getUomCode())
                            .build()));
        }

        // Generate product code
        String generatedCode = generateNextProductCode();

        Product product = Product.builder()
                .code(generatedCode)
                .name(productDTO.getName())
                .productType(productDTO.getProductType() != null ? ProductType.valueOf(productDTO.getProductType()) : null)
                .quantity(productDTO.getQuantity())
                .unitPrice(productDTO.getUnitPrice())
                .uom(uom)
                .build();

        Product savedProduct = productRepository.save(product);
        return toDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return toDTO(product);
    }

    @Override
    public void updateProduct(ProductDTO dto) {
        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getId()));

        UOM uom = uomRepository.findById(dto.getUomId())
                .orElseThrow(() -> new RuntimeException("UOM not found with id: " + dto.getUomId()));

        product.setName(dto.getName());
        product.setProductType(dto.getProductType() != null ? ProductType.valueOf(dto.getProductType()) : null);
        product.setUnitPrice(dto.getUnitPrice());
        product.setUom(uom);

        productRepository.save(product);
    }

    @Override
    public ProductDTO updateQuantity(Long id, Integer additionalQty) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        int updatedQty = (product.getQuantity() != null ? product.getQuantity() : 0) + additionalQty;
        product.setQuantity(updatedQty);

        return toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> searchProducts(String q) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(q);

        return products.stream()
                .map(p -> ProductDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .code(p.getCode())
                        .uomCode(p.getUom().getUomCode())
                        .unitPrice(p.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String generateNextProductCode() {
        Optional<Product> lastProductOpt = productRepository.findTopByOrderByIdDesc();
        int nextNumber = 1;

        if (lastProductOpt.isPresent()) {
            String lastCode = lastProductOpt.get().getCode();
            if (lastCode != null && lastCode.startsWith("P")) {
                try {
                    int number = Integer.parseInt(lastCode.substring(1));
                    nextNumber = number + 1;
                } catch (NumberFormatException ignored) {
                }
            }
        }

        return String.format("P%04d", nextNumber);
    }

    // Helper: convert entity to DTO
    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getProductType() != null ? product.getProductType().name() : null,
                product.getQuantity(),
                product.getUnitPrice(),
                product.getUom() != null ? product.getUom().getId() : null,
                product.getUom() != null ? product.getUom().getUomName() : null,
                product.getUom() != null ? product.getUom().getUomCode() : null
        );
    }
}
