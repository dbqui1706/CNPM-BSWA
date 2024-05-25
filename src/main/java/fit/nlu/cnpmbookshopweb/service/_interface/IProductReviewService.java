package fit.nlu.cnpmbookshopweb.service._interface;


import fit.nlu.cnpmbookshopweb.dto.ProductReviewDto;

import java.util.List;

public interface IProductReviewService extends IService<ProductReviewDto>{
    public List<ProductReviewDto> getOrderedPartByProductId(int limit, int offset, String orderBy, String orderDir, long productId);
    int countByProductId(long id);

    int sumRatingScoresByProductId(long id);
}
