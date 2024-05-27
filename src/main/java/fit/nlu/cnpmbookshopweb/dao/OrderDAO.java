package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.dao._interface.IOrderDAO;
import fit.nlu.cnpmbookshopweb.model.Order;
import fit.nlu.cnpmbookshopweb.model.OrderItem;
import fit.nlu.cnpmbookshopweb.model.User;
import fit.nlu.cnpmbookshopweb.utils.DatabaseConnector;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

public class OrderDAO extends AbstractDAO<Order> implements IOrderDAO {
    @Override
    public Long insert(Order order) {
        return DatabaseConnector.getJdbi().inTransaction(handle -> {
            String sql = "INSERT INTO orders (user_id, status, name_receiver, address_receiver, " +
                    "email_receiver, phone_receiver, delivery_method, delivery_price, createdAt, updatedAt" +
                    ") VALUES (:userIdOrdered, :status, " +
                    ":nameReceiver, :addressReceiver, :emailReceiver, :phoneReceiver, " +
                    ":deliveryMethod, :deliveryPrice, :createdAt, :updatedAt)";

            String sql2 = "INSERT INTO order_item (order_id, product_id, quantity, " +
                    "createdAt, updatedAt) VALUES (:order_id, :product_id, :quantity, " +
                    ":createdAt, :updatedAt)";
            Long id = handle.createUpdate(sql)
                    .bind("userIdOrdered", order.getUser().getId())
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
            for (OrderItem item : order.getOrderItems()) {
                long idOrderItem = handle.createUpdate(sql2)
                        .bind("order_id", id)
                        .bind("product_id", item.getProduct().getId())
                        .bind("quantity", item.getQuantity())
                        .bind("createdAt", new Timestamp(System.currentTimeMillis()))
                        .bind("updatedAt", Optional.ofNullable(null))
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Long.class).one();
            }
            return id;
        });
    }

    @Override
    public boolean update(Order order) {

        return false;
    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public Order getByID(Long orderId) {
        return DatabaseConnector.getJdbi().withHandle(handle -> {
            String sql = "SELECT o.id o_id, o.status o_status, o.name_receiver o_name_receiver, " +
                    "o.address_receiver o_address_receiver, o.email_receiver o_email_receiver, " +
                    "o.phone_receiver o_phone_receiver, o.delivery_price o_delivery_price, " +
                    "o.delivery_method o_delivery_method, o.createdAt o_createdAt, o.updatedAt o_updatedAt," +
                    "u.id u_id, u.username u_username, u.fullname u_fullname, u.password u_password, u.email u_email, " +
                    "u.phoneNumber u_phoneNumber, u.gender u_gender, u.address u_address, " +
                    "u.role u_role " +
                    "FROM orders o " +
                    "LEFT JOIN user_ u ON o.user_id = u.id " +
                    "WHERE o.id = :orderId";

            return new ArrayList<>(handle.createQuery(sql)
                    .bind("orderId", orderId)
                    .registerRowMapper(BeanMapper.factory(User.class, "u"))
                    .registerRowMapper(BeanMapper.factory(Order.class, "o"))
                    .reduceRows(new LinkedHashMap<Long, Order>(), (map, rowView) -> {
                        Order order = map.computeIfAbsent(
                                rowView.getColumn("o_id", Long.class),
                                id -> rowView.getRow(Order.class));

                        if (rowView.getColumn("u_id", Long.class) != null) {
                            order.setUser(rowView.getRow(User.class));
                        }
                        return map;
                    })
                    .values()).get(0);
        });
    }
}
