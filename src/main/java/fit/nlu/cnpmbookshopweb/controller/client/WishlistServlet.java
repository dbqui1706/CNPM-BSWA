package fit.nlu.cnpmbookshopweb.controller.client;

import fit.nlu.cnpmbookshopweb.dto.WishlistItemDto;
import fit.nlu.cnpmbookshopweb.service.ProductService;
import fit.nlu.cnpmbookshopweb.service.UserService;
import fit.nlu.cnpmbookshopweb.service.WishListItemService;
import fit.nlu.cnpmbookshopweb.utils.JsonUtil;
import fit.nlu.cnpmbookshopweb.utils.annotation.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {
    private final static int WISH_ITEM_PER_PAGE = 4;
    private final WishListItemService wishlistItemService = new WishListItemService();
    private final ProductService productService = new ProductService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đọc dữ liệu yêu thích từ yêu cầu dưới định dạng JSON

        WishlistItemRequest wishlistItemRequest = JsonUtil.get(request, WishlistItemRequest.class);
        String successMessage = "Đã thêm sản phẩm vào danh sách yêu thích thành công!";
        String errorMessage = "Đã có lỗi truy vấn!";

        // Hàm thực hiện khi thành công
        Runnable doneFunction = () -> JsonUtil.out(
                response,
                new Message(200, successMessage),
                HttpServletResponse.SC_OK);

        // Hàm thực hiện khi thất bại
        Runnable failFunction = () -> JsonUtil.out(
                response,
                new Message(404, errorMessage),
                HttpServletResponse.SC_NOT_FOUND);

        // Tạo đối tượng mục yêu thích từ dữ liệu yêu cầu
        WishlistItemDto wishlistItem = new WishlistItemDto();
//        wishlistItem.setUser(
//                userService.getById(wishlistItemRequest.getUserId()).get()
//        );
//        wishlistItem.setProduct(
//                productService.getById(wishlistItemRequest.getProductId()).get()
//        );

        // thêm mục yêu thích
        Optional<WishlistItemDto> res = wishlistItemService.insert(wishlistItem);
        if (res.isPresent()) doneFunction.run();
        else failFunction.run();
    }
}