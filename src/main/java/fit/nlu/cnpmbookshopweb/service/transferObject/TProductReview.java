package fit.nlu.cnpmbookshopweb.service.transferObject;


import fit.nlu.cnpmbookshopweb.dto.ProductReviewDto;
import fit.nlu.cnpmbookshopweb.model.ProductReview;

public class TProductReview implements ITransfer<ProductReviewDto, ProductReview>{
    @Override
    public ProductReviewDto toDto(ProductReview productReview) {
        ProductReviewDto productReviewDto = new ProductReviewDto();
        productReviewDto.setId(productReview.getId());
        productReviewDto.setRatingScore(productReview.getRatingScore());
        productReviewDto.setContent(productReview.getContent());
        productReviewDto.setIsShow(productReview.getIsShow());
        productReviewDto.setCreatedAt(productReview.getCreatedAt());
        productReviewDto.setUpdatedAt(productReview.getUpdatedAt());
        return productReviewDto;
    }

    @Override
    public ProductReview toEntity(ProductReviewDto productReviewDto) {
        ProductReview productReview = new ProductReview();
        productReview.setId(productReviewDto.getId());
        productReview.setProductId(productReviewDto.getProduct().getId());
        productReview.setUserId(productReviewDto.getUser().getId());
        productReview.setRatingScore(productReviewDto.getRatingScore());
        productReview.setContent(productReviewDto.getContent());
        productReview.setIsShow(productReviewDto.getIsShow());
        productReview.setCreatedAt(productReviewDto.getCreatedAt());
        productReview.setUpdatedAt(productReviewDto.getUpdatedAt());
        return productReview;
    }
}
