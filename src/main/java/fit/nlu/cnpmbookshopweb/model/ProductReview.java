package com.example.bookshopwebapplication.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@ToString
@NoArgsConstructor
public class ProductReview extends AbstractModel{
    private Long userId;
    private Long productId;
    private Integer ratingScore;
    private String content;
    private Integer isShow;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private User user;
    private Product product;

    public ProductReview(Long id, String modifiedBy, String createdBy, Location location, Long userId, Long productId, Integer ratingScore, String content, Integer isShow, Timestamp createdAt, Timestamp updatedAt, User user, Product product) {
        super(id, modifiedBy, createdBy, location);
        this.userId = userId;
        this.productId = productId;
        this.ratingScore = ratingScore;
        this.content = content;
        this.isShow = isShow;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.product = product;
    }
}
