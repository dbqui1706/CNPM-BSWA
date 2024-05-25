package fit.nlu.cnpmbookshopweb.model;

import fit.nlu.cnpmbookshopweb.model.AbstractModel;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview extends AbstractModel {
    private Long userId;
    private Long productId;
    private Integer ratingScore;
    private String content;
    private Integer isShow;
    private User user;
    private Product product;

}
