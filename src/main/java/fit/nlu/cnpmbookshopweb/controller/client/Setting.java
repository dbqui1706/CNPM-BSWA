package fit.nlu.cnpmbookshopweb.controller.client;

import fit.nlu.cnpmbookshopweb.model.User;
import fit.nlu.cnpmbookshopweb.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/setting")
public class Setting extends HttpServlet {
    private final UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("setting.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy thông tin người dùng
        User user = (User) session.getAttribute("currentUser");

        // Tạo một bảng lưu trữ các giá trị
        Map<String, String> values = new HashMap<>();
        values.put("username", request.getParameter("username"));
        values.put("fullname", request.getParameter("fullname"));
        values.put("email", request.getParameter("email"));
        values.put("phoneNumber", request.getParameter("phoneNumber"));
        values.put("gender", request.getParameter("gender"));
        values.put("address", request.getParameter("address"));

        // newUser nhận thông tin cập nhật
        User newUser = new User(
                values.get("username"),
                user.getPassword(),
                values.get("fullname"),
                values.get("email"),
                values.get("phoneNumber"),
                Integer.parseInt(values.get("gender")),
                values.get("address"),
                "CUSTOMER"
        );

        // danh sách những tên trong database
        ArrayList<String> listUsername = userService.getAllUsernames();

        // Chuỗi thông báo khi cập nhật thành công và khi cập nhật không thành công
        String successMessage = "Cập nhật thành công!";
        String errorMessage = "Cập nhật không thành công!";


        if(listUsername.contains(values.get("username"))){
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("user", user);
            request.getRequestDispatcher("setting.jsp").forward(request, response);
        }
        else {
            // Ngược lại, cập nhật thành công
            userService.update(newUser);
            request.setAttribute("successMessage", successMessage);
            request.setAttribute("user", newUser);
            request.getSession().setAttribute("currentUser", newUser);
            request.getRequestDispatcher("setting.jsp").forward(request, response);
        }

    }
}