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
        User curentuser = (User) session.getAttribute("currentUser");

        // nhận thông tin từ request
        String username =  request.getParameter("username");
        String fullname =  request.getParameter("fullname");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

        // newUser nhận thông tin cập nhật
        User newUser = new User(
                curentuser.getId(),
                username,
                curentuser.getPassword(),
                fullname,
                email,
                phoneNumber,
                Integer.parseInt(gender),
                address,
                "CUSTOMER"
        );

        // danh sách những tên trong database
        ArrayList<String> listUsername = userService.getAllUsernames();

        // Chuỗi thông báo khi cập nhật thành công và khi cập nhật không thành công
        String successMessage = "Cập nhật thành công!";
        String errorMessage = "Cập nhật không thành công!";


        if(listUsername.contains(username)){
            errorMessage = "Username đã tồn tại trong hệ thống";
            request.setAttribute("errorMessage", errorMessage);
//            request.setAttribute("user", curentuser);
            request.getRequestDispatcher("setting.jsp").forward(request, response);
        }
        else {
            // Ngược lại, kiểm tra cập nhật thành công hay không
            if(userService.update(newUser)){
                request.setAttribute("successMessage", successMessage);
//                request.setAttribute("user", newUser);
                request.getSession().setAttribute("currentUser", newUser);
                request.getRequestDispatcher("setting.jsp").forward(request, response);
            }else {
                request.setAttribute("errorMessage", errorMessage);
//                request.setAttribute("user", curentuser);
                request.getRequestDispatcher("setting.jsp").forward(request, response);
            }
        }

    }
}