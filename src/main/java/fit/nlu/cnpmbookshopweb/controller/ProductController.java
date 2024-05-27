package fit.nlu.cnpmbookshopweb.controller.client;

import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.model.User;
import fit.nlu.cnpmbookshopweb.service.ProductService;
import fit.nlu.cnpmbookshopweb.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(urlPatterns = {"/product"})
public class ProductController extends HttpServlet {
    private final ProductService productService = new ProductService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Tạo một user giả
        User fakeSessionUser = userService.getByID(1L);
        request.getSession().setAttribute("currentUser", fakeSessionUser);
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
}
