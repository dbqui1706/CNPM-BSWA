package fit.nlu.cnpmbookshopweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends AbstractModel {
    private Long orderID;
    private Long productID;
    private int quantity;
}
