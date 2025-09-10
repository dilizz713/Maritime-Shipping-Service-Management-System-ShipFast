package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ProductDTO;
import lk.ijse.gdse71.backend.service.ProductService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<APIResponse> saveProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProduct = productService.saveProduct(productDTO);
        APIResponse response = new APIResponse(200, "Product saved successfully", savedProduct);
        log.info("POST /save response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        APIResponse response = new APIResponse(200, "All products fetched successfully", products);
        log.info("GET /getAll response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/next-code")
    public ResponseEntity<APIResponse> getNextProductCode() {
        String nextCode = productService.generateNextProductCode();
        APIResponse response = new APIResponse(200, "Next product code fetched successfully", nextCode);
        log.info("GET /next-code response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        APIResponse response = new APIResponse(200, "Product fetched successfully", productDTO);
        log.info("GET /{} response: {}", id, response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateProduct(@RequestBody ProductDTO dto) {
        productService.updateProduct(dto);
        APIResponse response = new APIResponse(200, "Product updated successfully", null);
        log.info("PUT /update response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-qty")
    public ResponseEntity<APIResponse> updateQuantity(@RequestBody ProductDTO dto) {
        ProductDTO updatedProduct = productService.updateQuantity(dto.getId(), dto.getQuantity());
        APIResponse response = new APIResponse(200, "Product quantity updated successfully", updatedProduct);
        log.info("PUT /update-qty response: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        APIResponse response = new APIResponse(200, "Product deleted successfully", null);
        log.info("DELETE /delete/{} response: {}", id, response);
        return ResponseEntity.ok(response);
    }
}
