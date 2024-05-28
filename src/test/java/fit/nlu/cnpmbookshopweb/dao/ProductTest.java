package fit.nlu.cnpmbookshopweb.dao;

import fit.nlu.cnpmbookshopweb.dto.ProductReviewDto;
import fit.nlu.cnpmbookshopweb.service.ProductReviewService;
import fit.nlu.cnpmbookshopweb.service.ProductService;
import fit.nlu.cnpmbookshopweb.service.UserService;
import fit.nlu.cnpmbookshopweb.utils.Protector;
import fit.nlu.cnpmbookshopweb.utils.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductTest {
//    @Test
//    void validationProductReview() {
//        Map<String, List<String>> violations = new HashMap<>();
//        // ratingScoreViolations
//        violations.put("ratingScoreViolations", Validator.of("5")
//                .isNotNull()
//                .toList());
//        // contentViolations
//        violations.put("contentViolations", Validator.of("Good product!")
//                .isNotNullAndEmpty()
//                .isAtLeastOfLength(10)
//                .toList());
//
//        // dem so loi
//        int sumOfViolations = violations.values().stream().mapToInt(List::size).sum();
//
//        assertEquals(0, sumOfViolations);
//
//    }

    @Test
    void saveProductReview() {

        assertDoesNotThrow(() -> {
        ProductReviewService productReviewService = new ProductReviewService();
        ProductService productService = new ProductService();

        Map<String, String> values = new HashMap<>();
        values.put("userId", "1");
        values.put("productId", "1");
        values.put("ratingScore", "5");
        values.put("content", "Good product!");

        // create ProductReview
        ProductReviewDto productReview = new ProductReviewDto(
                0L,
                Protector.of(() -> Integer.parseInt(values.get("ratingScore"))).get(0),
                values.get("content"),
                1,
                new Timestamp(System.currentTimeMillis()),
                null,
                Protector.of(() -> new UserService()
                        .getByID(Long.parseLong(values.get("userId"))).get()).get().get(),
                Protector.of(() -> productService.getByID(
                        Long.parseLong(values.get("productId"))).get()).get().get()
        );

        // insert ProductReview
        Protector.of(() -> productReviewService.insert(productReview));
        });

    }
}
