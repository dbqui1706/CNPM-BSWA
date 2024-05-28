package fit.nlu.cnpmbookshopweb.controller.client;

import fit.nlu.cnpmbookshopweb.dto.*;
import fit.nlu.cnpmbookshopweb.model.Order;
import fit.nlu.cnpmbookshopweb.model.OrderItem;
import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.model.User;
import fit.nlu.cnpmbookshopweb.service.*;
import fit.nlu.cnpmbookshopweb.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    private final ProductService productService = new ProductService();
    private final UserService userService = new UserService();
    private final ProductReviewService productReviewService = new ProductReviewService();
    private static final int PRODUCT_REVIEWS_PER_PAGE = 2;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User fakeSessionUser = userService.getByID(1L);
        request.getSession().setAttribute("currentUser", fakeSessionUser);
        // Lấy id của product và đối tượng product từ database theo id này
        long id = 1L;
        Optional<ProductDto> productFromServer = Optional.ofNullable(productService.getByID(id).get());
        if (productFromServer.isPresent()) {

            // Lấy product từ productFromServer
            ProductDto product = productFromServer.get();
            product.setDescription(TextUtils.toParagraph(
                    Optional.ofNullable(product.getDescription()).orElse(""))
            );

            // Lấy tổng số đánh giá (productReview) của sản phẩm
            int totalProductReviews = productReviewService.countByProductId(id);

            // Lấy trang đánh giá hiện tại, gặp ngoại lệ (chuỗi không phải số, nhỏ hơn 1, lớn hơn tổng số trang)
            // thì gán bằng 1
            String pageReviewParam = Optional.ofNullable(request.getParameter("pageReview")).orElse("1");
            int pageReview = Integer.parseInt(pageReviewParam);

            // Tính tổng số Reviews
            int totalPagesOfProductReviews = Paging.totalPages(totalProductReviews, PRODUCT_REVIEWS_PER_PAGE);

            // Tính mốc truy vấn (offset)
            int offset = Paging.offset(pageReview, totalPagesOfProductReviews, PRODUCT_REVIEWS_PER_PAGE);

            // Lấy các productReview theo productId
            List<ProductReviewDto> productReviews = productReviewService.getOrderedPartByProductId(
                    PRODUCT_REVIEWS_PER_PAGE, offset, "createdAt", "DESC", id
            );
            productReviews.forEach(productReview -> productReview.setContent(
                    TextUtils.toParagraph(productReview.getContent())));

            // Lấy tổng cộng số sao đánh giá của sản phẩm
            int sumRatingScores = productReviewService.sumRatingScoresByProductId(id);

            // Tính số sao đánh giá trung bình
            int averageRatingScore = (totalProductReviews == 0) ? 0 : (sumRatingScores / totalProductReviews);


            // Kiểm tra có phải là sản phẩm yêu thích
            Optional<UserDto> currentUser = Optional.ofNullable(
                    ((User) request.getSession().getAttribute("currentUser")).get()
            );

            request.setAttribute("product", product);
            request.setAttribute("totalProductReviews", totalProductReviews);
            request.setAttribute("productReviews", productReviews);
            request.setAttribute("totalPagesOfProductReviews", totalPagesOfProductReviews);
            request.setAttribute("pageReview", pageReview);
            request.setAttribute("averageRatingScore", averageRatingScore);
            request.getRequestDispatcher("/views/client/product.jsp").forward(request, response);
        } else {
            // Nếu id không phải là số nguyên hoặc không hiện diện trong bảng product
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    // Xử lý thêm review




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = new ProductService();
        ProductReviewService productReviewService = new ProductReviewService();

        //3.2  doPost(userId, productId, ratingScore, content) gửi về controller
        Map<String, String> values = new HashMap<>();
        values.put("userId", req.getParameter("userId"));
        values.put("productId", req.getParameter("productId"));
        values.put("ratingScore", req.getParameter("ratingScore"));
        values.put("content", req.getParameter("content"));

        System.out.println("UserID: " + req.getParameter("userId"));
        System.out.println("ProductID: " + req.getParameter("productId"));
        System.out.println("Rating Score: " + req.getParameter("ratingScore"));
        System.out.println("Content: " + req.getParameter("content"));

        // 4. Validate(ratingScore, content)
        Map<String, List<String>> violations = new HashMap<>();
        violations.put("ratingScoreViolations", Validator.of(values.get("ratingScore")) //kiem tra tinh hop le cua ratingScore
                .isNotNull() // khong null
                .toList());
        violations.put("contentViolations", Validator.of(values.get("content")) // kiem tra tinh hop le cua content
                .isNotNullAndEmpty() // kong null khong trong
                .isAtLeastOfLength(10) // do dai it nhat la 10
                .toList());

        int sumOfViolations = violations.values().stream().mapToInt(List::size).sum();

        String successMessage = "Đã đánh giá thành công!";
        String errorAddReviewMessage = "Đã có lỗi truy vấn!";
        AtomicReference<String> anchor = new AtomicReference<>("");

        // Fragment validate
        // Nếu validate thành công
        if (sumOfViolations == 0) { // ok

            // 5.1 create ProductReviewDto
            ProductReviewDto productReview = new ProductReviewDto(
                    0L,
                    Protector.of(() -> Integer.parseInt(values.get("ratingScore"))).get(0), // chuyen doi ratingScore sang kieu so nguyen
                    values.get("content"),
                    1,
                    new Timestamp(System.currentTimeMillis()),
                    null,
                    Protector.of(() -> new UserService()
                            .getByID(Long.parseLong(values.get("userId"))).get()).get().get(), //lay thong tin nguoi dung theo userID
                    Protector.of(() -> productService.getByID(
                            Long.parseLong(values.get("productId"))).get()).get().get() // lay thong tin san pham theo productID
            );

            // 5.2 insert ProductReview
            Protector.of(() -> productReviewService.insert(productReview))

                    // Fragment: Save thành công hay ko 
                    //6.  Thành công: => Thông báo cho người dùng là đã đánh giá thành công

                    .done(run -> {
                        req.getSession().setAttribute("successMessage", successMessage);
                        anchor.set("#review");
                    })

                    // Thất bại: => Thông báo LỖI: đã có lỗi truy vấn
                    .fail(exception -> {
                        req.getSession().setAttribute("values", values);
                        req.getSession().setAttribute("errorAddReviewMessage", errorAddReviewMessage);
                        anchor.set("#review-form");
                    });
            // end fragment insert
            // end Success part of alt fragment validate

            // secondary case of validate fragment
        } else {
            // thông báo lỗi vào input
            req.getSession().setAttribute("values", values);
            req.getSession().setAttribute("violations", violations);
            anchor.set("#review-form");
        }

        resp.sendRedirect(req.getContextPath() + "/product?id=" + values.get("productId") + anchor);
    }
}
