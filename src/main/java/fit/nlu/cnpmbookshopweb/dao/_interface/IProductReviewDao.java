package fit.nlu.cnpmbookshopweb.dao._interface;


import fit.nlu.cnpmbookshopweb.dao.IGenericDAO;
import fit.nlu.cnpmbookshopweb.model.ProductReview;

import java.util.List;

public interface IProductReviewDao extends IGenericDAO<ProductReview> {
    List<ProductReview> getOrderedPartByProductId(int limit, int offset, String orderBy, String orderDir, long productId);
    int countByProductId(long productId);

    int sumRatingScoresByProductId(long productId);

    void hide(long id);

    void show(long id);
}
