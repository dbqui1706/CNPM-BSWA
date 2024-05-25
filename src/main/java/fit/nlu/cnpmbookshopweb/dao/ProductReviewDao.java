package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.dao._interface.IProductReviewDao;
import fit.nlu.cnpmbookshopweb.dao.mapper.ProductReviewMapper;
import fit.nlu.cnpmbookshopweb.model.ProductReview;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductReviewDao extends AbstractDAO<ProductReview> implements IProductReviewDao {

    // Phương thức để lưu đánh giá sản phẩm mới vào cơ sở dữ liệu
    public Long save(ProductReview pr) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "INSERT INTO product_review (userId, productId, ratingScore, content, isShow, createdAt, updatedAt) " +
                    "VALUES (:userId, :productId, :ratingScore, :content, :isShow, :createdAt, :updatedAt)";
            return handle.createUpdate(query)
                    .bind("userId", pr.getUserId())
                    .bind("productId", pr.getProductId())
                    .bind("ratingScore", pr.getRatingScore())
                    .bind("content", pr.getContent())
                    .bind("isShow", pr.getIsShow())
                    .bind("createdAt", pr.getCreatedAt())
                    .bind("updatedAt", new Timestamp(System.currentTimeMillis()))
                    .executeAndReturnGeneratedKeys()
                    .mapTo(Long.class)
                    .one();
        }
    }

    @Override
    public Long insert(ProductReview productReview) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "INSERT INTO product_review (userId, productId, ratingScore, content, isShow, createdAt, updatedAt) " +
                    "VALUES (:userId, :productId, :ratingScore, :content, :isShow, :createdAt, :updatedAt)";
            return handle.createUpdate(query)
                    .bind("userId", productReview.getUserId())
                    .bind("productId", productReview.getProductId())
                    .bind("ratingScore", productReview.getRatingScore())
                    .bind("content", productReview.getContent())
                    .bind("isShow", productReview.getIsShow())
                    .bind("createdAt", productReview.getCreatedAt())
                    .bind("updatedAt", new Timestamp(System.currentTimeMillis()))
                    .executeAndReturnGeneratedKeys()
                    .mapTo(Long.class)
                    .one();
        }
    }

    // Phương thức để cập nhật thông tin đánh giá sản phẩm trong cơ sở dữ liệu
    public void update(ProductReview pr) {
       Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "UPDATE product_review SET ratingScore = :ratingScore, content = :content, " +
                    "updatedAt = :updatedAt WHERE id = :id";
            handle.createUpdate(query)
                    .bind("ratingScore", pr.getRatingScore())
                    .bind("content", pr.getContent())
                    .bind("updatedAt", new Timestamp(System.currentTimeMillis()))
                    .bind("id", pr.getId())
                    .execute();
        }
    }

    @Override
    public void delete(ProductReview productReview) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "DELETE FROM product_review WHERE id = :id";
            handle.createUpdate(query)
                    .bind("id", productReview.getId())
                    .execute();
        }
    }

    @Override
    public ProductReview getByID(Long id) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "SELECT * FROM product_review WHERE id = :id";
            return handle.createQuery(query)
                    .bind("id", id)
                    .map(new ProductReviewMapper())
                    .findFirst()
                    .orElse(null);
        }
    }

    // Phương thức để xóa đánh giá sản phẩm từ cơ sở dữ liệu
    public void delete(Long id) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "DELETE FROM product_review WHERE id = :id";
            handle.createUpdate(query)
                    .bind("id", id)
                    .execute();
        }
    }

    // Phương thức để lấy đánh giá sản phẩm dựa trên ID
    public Optional<ProductReview> getById(Long id) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "SELECT * FROM product_review WHERE id = :id";
            return handle.createQuery(query)
                    .bind("id", id)
                    .map(new ProductReviewMapper())
                    .findFirst();
        }
    }

    // Phương thức để lấy một phần danh sách đánh giá sản phẩm từ cơ sở dữ liệu
    public List<ProductReview> getPart(Integer limit, Integer offset) {
        Jdbi jdbi = DatabaseConnector.getJdbi();

        try (Handle handle = jdbi.open()) {
            String query = "SELECT * FROM product_review LIMIT :offset, :limit";
            return handle.createQuery(query)
                    .bind("offset", offset)
                    .bind("limit", limit)
                    .map(new ProductReviewMapper())
                    .list();

        }
    }

    // Phương thức để lấy một phần danh sách đánh giá sản phẩm từ cơ sở dữ liệu với sắp xếp
    public List<ProductReview> getOrderedPart(Integer limit, Integer offset, String orderBy, String sort) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "SELECT * FROM product_review ORDER BY :orderBy :sort LIMIT :offset, :limit";
            return handle.createQuery(query)
                    .bind("orderBy", orderBy)
                    .bind("sort", sort)
                    .bind("offset", offset)
                    .bind("limit", limit)
                    .map(new ProductReviewMapper())
                    .list();
        }
    }

    // Phương thức để đếm số lượng đánh giá sản phẩm trong cơ sở dữ liệu
    public int count() {
       Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "SELECT COUNT(*) FROM product_review";
            return handle.createQuery(query)
                    .mapTo(Integer.class)
                    .one();
        }
    }

    // Phương thức để lấy một phần danh sách đánh giá sản phẩm từ cơ sở dữ liệu với sắp xếp theo sản phẩm và giới hạn số lượng
    @Override
    public List<ProductReview> getOrderedPartByProductId(int limit, int offset, String orderBy, String sort, long productId) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "SELECT pr.*, u.fullname " +
                    "FROM product_review pr " +
                    "JOIN user u ON pr.userId = u.id " +
                    "WHERE productId = :productId AND pr.isShow = 1 " +
                    "ORDER BY :orderBy :sort " +
                    "LIMIT :offset, :limit";
            return handle.createQuery(query)
                    .bind("productId", productId)
                    .bind("orderBy", orderBy)
                    .bind("sort", sort)
                    .bind("offset", offset)
                    .bind("limit", limit)
                    .map(new ProductReviewMapper())
                    .list();
        }
    }

    // Phương thức để đếm số lượng đánh giá sản phẩm dựa trên ID sản phẩm
    @Override
    public int countByProductId(long productId) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "SELECT COUNT(id) FROM product_review WHERE productId = :productId";
            return handle.createQuery(query)
                    .bind("productId", productId)
                    .mapTo(Integer.class)
                    .one();
        }
    }

    // Phương thức để tính tổng điểm đánh giá sản phẩm dựa trên ID sản phẩm
    @Override
    public int sumRatingScoresByProductId(long productId) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "SELECT SUM(ratingScore) FROM product_review WHERE productId = :productId";
            return handle.createQuery(query)
                    .bind("productId", productId)
                    .mapTo(Integer.class)
                    .one();
        }
    }

    // Phương thức để ẩn một đánh giá sản phẩm dựa trên ID
    @Override
    public void hide(long id) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "UPDATE product_review SET isShow = 0, updatedAt = :updatedAt WHERE id = :id";
            handle.createUpdate(query)
                    .bind("updatedAt", new Timestamp(System.currentTimeMillis()))
                    .bind("id", id)
                    .execute();
        }
    }

    // Phương thức để hiển thị một đánh giá sản phẩm dựa trên ID
    @Override
    public void show(long id) {
        Jdbi jdbi = DatabaseConnector.getJdbi();
        try (Handle handle = jdbi.open()) {
            String query = "UPDATE product_review SET isShow = 1, updatedAt = :updatedAt WHERE id = :id";
            handle.createUpdate(query)
                    .bind("updatedAt", new Timestamp(System.currentTimeMillis()))
                    .bind("id", id)
                    .execute();
        }
    }


}
