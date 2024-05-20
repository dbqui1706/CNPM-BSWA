package fit.nlu.cnpmbookshopweb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@ToString
public class WishListItem extends AbstractModel{
    private Long userId;
    private Long productId;
    private Timestamp createdAt;
    private Product product;
    private User user;

    public WishListItem(Long id, String modifiedBy,
                        String createdBy, Location location,
                        Long userId, Long productId, Timestamp createdAt,
                        Product product, User user) {
        this.userId = userId;
        this.productId = productId;
        this.createdAt = createdAt;
        this.product = product;
        this.user = user;
    }

    public WishListItem(Long userId, Long productId, Timestamp createdAt, Product product, User user) {
        this.userId = userId;
        this.productId = productId;
        this.createdAt = createdAt;
        this.product = product;
        this.user = user;
    }
}
