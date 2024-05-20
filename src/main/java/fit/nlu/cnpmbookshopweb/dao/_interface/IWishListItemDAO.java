package fit.nlu.cnpmbookshopweb.dao._interface;

public interface IWishListItemDAO {
    int countByUserIdAndProductId(Long userId);
}
