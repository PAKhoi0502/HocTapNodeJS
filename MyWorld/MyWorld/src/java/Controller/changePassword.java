/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "changePassword", urlPatterns = {"/changePassword"})
public class changePassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session hiện tại, không tạo mới
        if (session == null || session.getAttribute("userId") == null) {
            // Nếu session không tồn tại hoặc không có userId, chuyển hướng về trang đăng nhập
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId"); // Lấy userId từ session

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp không
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Xác nhận mật khẩu mới không khớp!");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu hiện tại
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);

        if (user == null || !user.getPassword().equals(currentPassword)) {
            request.setAttribute("message", "Mật khẩu hiện tại không đúng!");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mới không được trùng với mật khẩu hiện tại
        if (currentPassword.equals(newPassword)) {
            request.setAttribute("message", "Mật khẩu mới không được trùng với mật khẩu hiện tại!");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        // Thay đổi mật khẩu
        boolean isUpdated = userDAO.updatePassword(userId, currentPassword, newPassword);
        if (isUpdated) {
            request.setAttribute("message", "Thay đổi mật khẩu thành công!");
        } else {
            request.setAttribute("message", "Có lỗi xảy ra. Vui lòng thử lại.");
        }
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }
}


