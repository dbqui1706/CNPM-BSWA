package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.model.OrderItem;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;

import java.sql.Timestamp;

public class OrderItemDAO extends AbstractDAO<OrderItem> {
    @Override
    public Long insert(OrderItem orderItem) {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "INSERT INTO order_item (order_id, product_id, quantity, " +
                    "createdAt, updatedAt) VALUES (:order_id, :product_id, :quantity, " +
                    ":createdAt, :updatedAt)";
            return handle.createUpdate(sql)
                    .bind("order_id", orderItem.getOrderID())
                    .bind("product_id", orderItem.getProductID())
                    .bind("quantity", orderItem.getQuantity())
                    .bind("createdAt", new Timestamp(System.currentTimeMillis()))
//                    .bind("updatedAt", orderItem.getUpdatedAt())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Long.class).one();
        });
    }

    @Override
    public boolean update(OrderItem orderItem) {

        return false;
    }

    @Override
    public void delete(OrderItem orderItem) {

    }

    @Override
    public OrderItem getByID(Long id) {
        return null;
    }
}