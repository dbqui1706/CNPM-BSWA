
package fit.nlu.cnpmbookshopweb.controller.client;


import fit.nlu.cnpmbookshopweb.dao._interface.IWishListDAO;
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
public class AddWishList extends HttpServlet {

    private final IWishListDAO iWishListDao = new WishListDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");

        User fakeSessionUser = (User) req.getSession().getAttribute("currentUser");
        Product fakeProduct = (Product) req.getSession().getAttribute("product");

        if (fakeSessionUser == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Vui Lòng Đăng Nhập Để Thêm Vào Danh Sách Yêu Thích!");
            return;
        }


        int count =iWishListDao.countByUserIdAndProductId(fakeSessionUser.getId(), fakeProduct.getId());

        if (count > 0) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Sản Phẩm Đã Tồn Tại Trong Danh Sách Yêu Thích!");
            return;
        }


        boolean success = iWishListDao.addProductToWishlist(fakeSessionUser.getId(), fakeProduct.getId());

        if (success) {
            resp.getWriter().write("Thêm Vào Danh Sách Yêu Thích Thành Công!");
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã Xảy Ra Lỗi Khi Thêm Vào Danh Sách Yêu Thích!");
        }

    }

}
