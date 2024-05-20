package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.dao._interface.IWishListItemDAO;
import fit.nlu.cnpmbookshopweb.model.WishListItem;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class WishListItemDAO implements IWishListItemDAO {
    @Override
    public int countByUserIdAndProductId(Long userId) {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "SELECT COUNT(id) FROM wishlist_item WHERE userId = :userId";
            return handle.createQuery(sql)
                    .bind("userId", userId)
                    .mapTo(Integer.class)
                    .one();

        });

    }
    public Long save(WishListItem wishListItem) {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "INSERT INTO wishlist_item (userId, productId, createdAt) VALUES(?, ?, ?)";
            return handle.createUpdate(sql)
                    .bind("userId", wishListItem.getUserId())
                    .bind("productId", wishListItem.getProductId())
                    .bind("createdAt", new Timestamp(System.currentTimeMillis()))
                    .executeAndReturnGeneratedKeys()
                    .mapTo(Long.class)
                    .one();
        });
    }
    // Phương thức để lấy thông tin một mục Wishlist dựa trên ID
    public Optional<WishListItem> getById(Long id) {
            return DatabaseConnector.getJdbi().withHandle(handle -> {
                String sql = "SELECT * FROM wishlist_item WHERE id = :id";
                return handle.createQuery(sql)
                        .bind("id", id)
                        .mapToBean(WishListItem.class)
                        .findFirst();
            });
        }
}
