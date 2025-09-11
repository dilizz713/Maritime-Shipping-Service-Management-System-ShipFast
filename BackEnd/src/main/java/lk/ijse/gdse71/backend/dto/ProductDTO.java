package lk.ijse.gdse71.backend.dto;

import lk.ijse.gdse71.backend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private String productType;
    private Integer quantity;
    private Double unitPrice;

    private Long uomId;
    private String uomName;
    private String uomCode;


    public ProductDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
