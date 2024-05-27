package fit.nlu.cnpmbookshopweb.controller;

import fit.nlu.cnpmbookshopweb.dto.RequestOrderBuyNow;
import fit.nlu.cnpmbookshopweb.dto.ResponseProductDetail;
import fit.nlu.cnpmbookshopweb.model.*;
import fit.nlu.cnpmbookshopweb.service.OrderService;
import fit.nlu.cnpmbookshopweb.service.ProductService;
import fit.nlu.cnpmbookshopweb.utils.JsonUtil;
import fit.nlu.cnpmbookshopweb.utils.Protector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/buy-now"})
public class OrderController extends HttpServlet {
    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy id sản phẩm và số lượng của sản phẩm
        Long productID = Long.parseLong(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        // Lấy sản phẩm theo ID
        Product product = productService.getByID(productID);

        // Tính toán giá cho sản phẩm mà người dùng yêu cầu
        Double tempPrice = product.getDiscount() == 0
                ? product.getPrice() * quantity
                : product.getPrice() * product.getDiscount() * quantity;

        // Trả về cho người dùng chi tiết số lượng và sản phẩm người dùng đã đặt
        ResponseProductDetail productDetail = ResponseProductDetail.builder()
                .productID(productID)
                .nameProduct(product.getName())
                .imageName(product.getImageName())
                .price(product.getDiscount() == 0
                        ? product.getPrice()
                        : product.getPrice() * product.getDiscount())
                .quantityBuy(quantity)
                .total(product.getDiscount() == 0
                        ? product.getPrice() * quantity
                        : product.getPrice() * product.getDiscount() * quantity)
                .build();
        List<ResponseProductDetail> productDetails = Arrays.asList(productDetail);

        request.setAttribute("product", product);
        request.setAttribute("productDetails", productDetails);
        request.setAttribute("tempPrice", tempPrice);

        // Trả về giao diện cho người dùng
        request.getRequestDispatcher("/views/client/buyNow.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nhận dữ liệu từ front-end
        RequestOrderBuyNow requestOrder = JsonUtil.get(request, RequestOrderBuyNow.class);

        // Lấy người dùng đang đặt hàng.
        User sessionUser = (User) request.getSession().getAttribute("currentUser");

        String formatAddress = String.format("%s, %s, %s, %s",
                requestOrder.getAddress(), requestOrder.getCity(),
                requestOrder.getDistrict(), requestOrder.getWard());

        // Tạo một đơn đặt hàng.
        Order order = new Order();
        order.setUser(sessionUser);
        order.setStatus(0);
        order.setNameReceiver(requestOrder.getFirstname() + requestOrder.getLastname());
        order.setAddressReceiver(formatAddress);
        order.setEmailReceiver(requestOrder.getEmail());
        order.setPhoneReceiver(requestOrder.getPhoneNumber());
        switch (requestOrder.getDelivery()) {
            case "EXPRESS":
                order.setDeliveryMethod(2);
                order.setDeliveryPrice(50000.0);
            default:
                order.setDeliveryMethod(1);
                order.setDeliveryPrice(15000.0);
        }
        Product product = productService.getByID(requestOrder.getProductId());

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(requestOrder.getQuantity());
        order.setOrderItems(Arrays.asList(orderItem));

        // Lưu đơn đặt hàng xuống database
        Optional<Long> orderID = Protector
                .of(() -> orderService.save(order))
                .done(d -> response.setStatus(HttpServletResponse.SC_CREATED))
                .fail(f -> response.setStatus(HttpServletResponse.SC_BAD_REQUEST))
                .get();

    }
}
