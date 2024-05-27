package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.OrderDAO;
import fit.nlu.cnpmbookshopweb.dao.OrderItemDAO;
import fit.nlu.cnpmbookshopweb.model.OrderItem;
import fit.nlu.cnpmbookshopweb.model.Order;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    public Long save(Order order) {
        Long id = orderDAO.insert(order);
        return id;
    }
}
