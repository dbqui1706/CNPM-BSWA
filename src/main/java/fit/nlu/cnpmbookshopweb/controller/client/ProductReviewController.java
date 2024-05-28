package fit.nlu.cnpmbookshopweb.controller.client;

import fit.nlu.cnpmbookshopweb.dto.ProductReviewDto;
import fit.nlu.cnpmbookshopweb.service.ProductReviewService;
import fit.nlu.cnpmbookshopweb.service.ProductService;
import fit.nlu.cnpmbookshopweb.service.UserService;
import fit.nlu.cnpmbookshopweb.utils.Protector;
import fit.nlu.cnpmbookshopweb.utils.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@WebServlet("/product-review")
public class ProductReviewController extends HttpServlet {
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
