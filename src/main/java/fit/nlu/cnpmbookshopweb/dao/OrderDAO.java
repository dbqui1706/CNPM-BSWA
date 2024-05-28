package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.dao._interface.IOrderDAO;
import fit.nlu.cnpmbookshopweb.model.Order;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;

import java.sql.Timestamp;
import java.util.Optional;

public class OrderDAO extends AbstractDAO<Order> implements IOrderDAO {
    @Override
    public Long insert(Order order) {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "INSERT INTO orders (user_id, status, name_receiver, address_receiver, " +
                    "email_receiver, phone_receiver, delivery_method, delivery_price, createdAt, updatedAt" +
                    ") VALUES (:userIdOrdered, :status, " +
                    ":nameReceiver, :addressReceiver, :emailReceiver, :phoneReceiver, " +
                    ":deliveryMethod, :deliveryPrice, :createdAt, :updatedAt)";
            return handle.createUpdate(sql)
                    .bind("userIdOrdered", order.getUserIdOrdered())
                    .bind("status", order.getStatus())
                    .bind("nameReceiver", order.getNameReceiver())
                    .bind("addressReceiver", order.getAddressReceiver())
                    .bind("emailReceiver", order.getEmailReceiver())
                    .bind("phoneReceiver", order.getPhoneReceiver())
                    .bind("deliveryMethod", order.getDeliveryMethod())
                    .bind("deliveryPrice", order.getDeliveryPrice())
                    .bind("createdAt", new Timestamp(System.currentTimeMillis()))
                    .bind("updatedAt", Optional.ofNullable(null))
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Long.class)
                    .one();
        });
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public Order getByID(Long id) {
        return null;
    }
}