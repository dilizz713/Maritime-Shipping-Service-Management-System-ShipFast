package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);

    List<ProductDTO> getAllProducts();

    String generateNextProductCode();

    ProductDTO getProductById(Long id);

    void updateProduct(ProductDTO dto);

    ProductDTO updateQuantity(Long id, Integer quantity);

    void deleteProduct(Long id);

    List<ProductDTO> searchProducts(String q);
}
