package fit.nlu.cnpmbookshopweb.controller.client;

import fit.nlu.cnpmbookshopweb.dto.RequestOrderBuyNow;
import fit.nlu.cnpmbookshopweb.dto.ResponseProductDetail;
import fit.nlu.cnpmbookshopweb.model.Order;
import fit.nlu.cnpmbookshopweb.model.OrderItem;
import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.model.User;
import fit.nlu.cnpmbookshopweb.service.OrderItemService;
import fit.nlu.cnpmbookshopweb.service.OrderService;
import fit.nlu.cnpmbookshopweb.service.ProductService;
import fit.nlu.cnpmbookshopweb.service.UserService;
import fit.nlu.cnpmbookshopweb.utils.JsonUtil;
import fit.nlu.cnpmbookshopweb.utils.Protector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(urlPatterns = {"/product", "/buy-now", "/validation-info"})
public class ProductController extends HttpServlet {
    private final ProductService productService = new ProductService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tạo một user giả
        User fakeSessionUser = userService.getByID(1L);
        request.getSession().setAttribute("currentUser", fakeSessionUser);
        String uri = request.getRequestURI();
        switch (uri) {
            case "/buy-now":
                sendRedirectBuyNow(request, response);
                return;
            case "validation-info":
                sendRedirectValidationInfo(request, response);
                return;
            default:
                sendRedirectProduct(request, response);
        }
    }


//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if (req.getRequestURI().equals("/buy-now")) {
//            RequestOrderBuyNow requestOrder = JsonUtil.get(req, RequestOrderBuyNow.class);
//            User sessionUser = (User) req.getSession().getAttribute("currentUser");
//            String formatAddress = String.format("%s, %s, %s, %s",
//                    requestOrder.getAddress(), requestOrder.getCity(),
//                    requestOrder.getDistrict(), requestOrder.getWard());
//
//            Order order = new Order();
//            order.setUserIdOrdered(sessionUser.getId());
//            order.setStatus(0);
//            order.setNameReceiver(requestOrder.getFirstname() + requestOrder.getLastname());
//            order.setAddressReceiver(formatAddress);
//            order.setEmailReceiver(requestOrder.getEmail());
//            order.setPhoneReceiver(requestOrder.getPhoneNumber());
//            switch (requestOrder.getDelivery()) {
//                case "EXPRESS":
//                    order.setDeliveryMethod(2);
//                    order.setDeliveryPrice(50000.0);
//                default:
//                    order.setDeliveryMethod(1);
//                    order.setDeliveryPrice(15000.0);
//            }
//
//            final OrderService orderService = new OrderService();
//            final OrderItemService orderItemService = new OrderItemService();
//
//            Optional<Long> orderID = Protector
//                    .of(() -> orderService.save(order))
//                    .done(d -> resp.setStatus(HttpServletResponse.SC_CREATED))
//                    .fail(f -> resp.setStatus(HttpServletResponse.SC_BAD_REQUEST)).get();
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrderID(orderID.get());
//            orderItem.setProductID(requestOrder.getProductId());
//            orderItem.setQuantity(requestOrder.getQuantity());
//            Protector.of(() -> orderItemService.save(orderItem))
//                    .done(d -> resp.setStatus(HttpServletResponse.SC_CREATED))
//                    .fail(f -> resp.setStatus(HttpServletResponse.SC_BAD_REQUEST));
//        }
//    }
//
    private void sendRedirectBuyNow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productID = Long.parseLong(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Product product = productService.getByID(productID);

        Double tempPrice = product.getDiscount() == 0
                ? product.getPrice() * quantity
                : product.getPrice() * product.getDiscount() * quantity;

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

        request.getRequestDispatcher("/views/client/buyNow.jsp").forward(request, response);
    }

    private void sendRedirectProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tạo một product giả
        Product fakeProduct = productService.getByID(1L);
        fakeProduct.setDescription(
                Optional.ofNullable(
                        Stream.of(fakeProduct.getDescription().split("(\r\n|\n)"))
                                .filter(paragraph -> !paragraph.isEmpty())
                                .map(paragraph -> "<p>" + paragraph + "</p>")
                                .collect(Collectors.joining(""))
                ).orElse("")
        );
        // Lấy tổng số đánh giá (productReview) của sản phẩm
        int totalProductReviews = 150;
        request.setAttribute("product", fakeProduct);
        request.setAttribute("totalProductReviews", totalProductReviews);
        request.getRequestDispatcher("/views/client/product.jsp").forward(request, response);
    }

    private void sendRedirectValidationInfo(HttpServletRequest request, HttpServletResponse response) {

    }
}
