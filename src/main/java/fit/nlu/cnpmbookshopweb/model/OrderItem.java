package fit.nlu.cnpmbookshopweb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItem extends AbstractModel {
    private Order order;
    private Product product;
    private int quantity;

    public OrderItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }
}
