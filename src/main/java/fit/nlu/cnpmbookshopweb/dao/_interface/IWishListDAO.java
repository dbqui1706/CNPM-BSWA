package fit.nlu.cnpmbookshopweb.dao._interface;

public interface IWishListDAO {
    boolean addProductToWishlist(Long userId, Long productId);
    int countByUserIdAndProductId(Long userId, Long productId);
}
