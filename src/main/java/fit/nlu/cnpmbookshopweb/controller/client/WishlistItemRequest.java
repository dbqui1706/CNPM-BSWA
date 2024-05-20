package fit.nlu.cnpmbookshopweb.controller.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class WishlistItemRequest {
    private final long userId;
    private final long productId;
}
