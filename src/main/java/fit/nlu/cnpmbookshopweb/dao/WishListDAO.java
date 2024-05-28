package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;
import org.jdbi.v3.core.Jdbi;

public class WishListDAO {
//    Phương thức câu query thêm sản phẩm vào danh sách yêu thích
    public boolean addProductToWishlist(Long userId, Long productId) {
        if (userId == null || productId == null) {
            throw new IllegalArgumentException("User ID and Product ID must not be null");
        }

        Jdbi jdbi = DatabaseConnector.getJdbi();
        return jdbi.withHandle(handle -> {
            String sql = "INSERT INTO wishList (user_id, product_id, added_date) VALUES (:userId, :productId, CURRENT_TIMESTAMP)";
            int rowsAffected = handle.createUpdate(sql)
                    .bind("userId", userId)
                    .bind("productId", productId)
                    .execute();
            return rowsAffected > 0;
        });
    }
//    phương thức câu query đếm số sản phẩm và user để kiểm tra sự tồn tại
    public int countByUserIdAndProductId(Long userId, Long productId) {
        String sql = "SELECT COUNT(id) " +
                "FROM wishlist_item " +
                "WHERE userId = :userId AND productId = :productId";

        return DatabaseConnector.getJdbi().withHandle(handle ->
                handle.createQuery(sql)
                        .bind("userId", userId)
                        .bind("productId", productId)
                        .mapTo(Integer.class)
                        .one()
        );
    }
}