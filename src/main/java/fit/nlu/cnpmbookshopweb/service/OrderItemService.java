package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.OrderItemDAO;
import fit.nlu.cnpmbookshopweb.model.OrderItem;

public class OrderItemService {
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    public Long save(OrderItem orderItem) {
        return orderItemDAO.insert(orderItem);
    }
}
