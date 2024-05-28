package fit.nlu.cnpmbookshopweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductDetail {
    private Long productID;
    private String nameProduct;
    private String imageName;
    private Double price;
    private int quantityBuy;
    private double total;
}