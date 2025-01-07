package Controller;

import DAL.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "resetPassword", urlPatterns = {"/resetPassword"})
public class resetPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra mật khẩu mới có khớp không
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Mật khẩu không khớp!");
            request.getRequestDispatcher("resetPassword.jsp?token=" + token).forward(request, response);
            return;
        }

        // Kiểm tra token hợp lệ và cập nhật mật khẩu
        UserDAO userDAO = new UserDAO();
        boolean isUpdated = userDAO.updatePasswordWithToken(token, newPassword);

        if (isUpdated) {
            request.setAttribute("message", "Mật khẩu đã được cập nhật thành công!");
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("message", "Mã token không hợp lệ hoặc đã hết hạn!");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            response.getWriter().println("Token không hợp lệ!");
            return;
        }

        // Kiểm tra token trong cơ sở dữ liệu (nếu cần)
        UserDAO userDAO = new UserDAO();
        boolean isValidToken = userDAO.isValidToken(token);

        if (isValidToken) {
            // Chuyển hướng đến trang đặt lại mật khẩu
            request.setAttribute("token", token);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        } else {
            response.getWriter().println("Token không hợp lệ hoặc đã hết hạn!");
        }
    }

}
