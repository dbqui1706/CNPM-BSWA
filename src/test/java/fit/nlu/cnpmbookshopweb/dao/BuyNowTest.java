package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.model.Order;
import fit.nlu.cnpmbookshopweb.model.OrderItem;
import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.service.OrderItemService;
import fit.nlu.cnpmbookshopweb.service.OrderService;
import fit.nlu.cnpmbookshopweb.service.ProductService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;


import java.sql.Timestamp;
import java.util.Set;

class BuyNowTest {
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final OrderItemService orderItemService = new OrderItemService();

    @Test
    void BuyNow() {
        Validator validator;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        int quantity = 2;
        Long userID = 1L;
        Long product_ID = 1L;
        // get PRODUCT to show
        Product product = productService.getByID(product_ID);

        Order order = new Order();
        order.setUserIdOrdered(userID);
        order.setStatus(0);
        order.setNameReceiver("PTQH");
        order.setAddressReceiver("Di An, Binh Duong");
        order.setEmailReceiver("ptqhgmail.com");
        order.setPhoneReceiver("0132145645");
        order.setDeliveryMethod(0);
        order.setDeliveryPrice(15000.0);
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        System.out.println(violations);
        if (violations.isEmpty()){
            Long orderID = orderService.save(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderID(orderID);
            orderItem.setProductID(product_ID);
            orderItem.setQuantity(quantity);

            orderItemService.save(orderItem);
        }

    }
}