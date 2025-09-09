package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);

    List<ProductDTO> getAllProducts();
}
