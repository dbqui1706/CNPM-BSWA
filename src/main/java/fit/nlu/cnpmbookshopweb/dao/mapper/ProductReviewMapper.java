package fit.nlu.cnpmbookshopweb.dao.mapper;

import fit.nlu.cnpmbookshopweb.dao.ProductReviewDao;
import fit.nlu.cnpmbookshopweb.dao.mapper._interface.IRowMapper;
import fit.nlu.cnpmbookshopweb.model.ProductReview;
import fit.nlu.cnpmbookshopweb.service.ProductService;
import fit.nlu.cnpmbookshopweb.service.UserService;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductReviewMapper implements RowMapper<ProductReview> {
    @Override
    public ProductReview map(ResultSet resultSet, StatementContext statementContext) throws SQLException {
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        return new ProductReview(
                resultSet.getLong("userId"),
                resultSet.getLong("productId"),
                resultSet.getInt("ratingScore"),
                resultSet.getString("content"),
                resultSet.getInt("isShow"),
                userService.getByID(resultSet.getLong("userId")),
                productService.getByID(resultSet.getLong("productId"))
        );
            }
}
