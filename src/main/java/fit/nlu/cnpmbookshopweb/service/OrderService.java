package fit.nlu.cnpmbookshopweb.service;

import fit.nlu.cnpmbookshopweb.dao.OrderDAO;
import fit.nlu.cnpmbookshopweb.model.Order;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    public Long save(Order order){
        return orderDAO.insert(order);
    }
}