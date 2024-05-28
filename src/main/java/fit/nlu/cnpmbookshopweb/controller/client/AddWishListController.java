
package fit.nlu.cnpmbookshopweb.controller.client;


import fit.nlu.cnpmbookshopweb.dao.WishListDAO;
import fit.nlu.cnpmbookshopweb.model.Product;
import fit.nlu.cnpmbookshopweb.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addWishList")
public class AddWishListController extends HttpServlet {

    private final WishListDAO iWishListDao = new WishListDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");
    // Lấy user và product trên session
        User SessionUser = (User) req.getSession().getAttribute("currentUser");
        Product Product = (Product) req.getSession().getAttribute("product");
    // nếu chưa đăng nhập
        if (SessionUser == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Vui Lòng Đăng Nhập Để Thêm Vào Danh Sách Yêu Thích!");
            return;
        }

//      đếm số sản phẩm và user để kiểm tra sự tồn tại
        int count =iWishListDao.countByUserIdAndProductId(SessionUser.getId(),Product.getId());
// nếu đã tồn tại thì thông chuyển response đến product jsp để thông báo
        if (count > 0) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Sản Phẩm Đã Tồn Tại Trong Danh Sách Yêu Thích!");
            return;
        }

// thêm sản phẩm vào danh sách yêu thích
        boolean success = iWishListDao.addProductToWishlist(SessionUser.getId(), Product.getId());
// thêm thành công thì gửi thông báo
        if (success) {
            resp.getWriter().write("Thêm Vào Danh Sách Yêu Thích Thành Công!");
//            gửi thông báo khi thêm thất bại
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã Xảy Ra Lỗi Khi Thêm Vào Danh Sách Yêu Thích!");
        }

    }

}