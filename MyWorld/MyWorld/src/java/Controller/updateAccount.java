package Controller;


import DAL.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "updateAccount", urlPatterns = {"/updateAccount"})
public class updateAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        UserDAO userDAO = new UserDAO();

        // Kiểm tra trùng lặp
        String duplicateField = userDAO.checkDuplicateForUpdate(userId, username, email, phone);
        if (duplicateField != null) {
            request.setAttribute("message", duplicateField + " đã tồn tại. Vui lòng nhập thông tin khác.");
            request.getRequestDispatcher("manageAccount.jsp").forward(request, response);
            return;
        }

        // Thực hiện cập nhật
        boolean isUpdated = userDAO.updateUser(userId, username, email, phone);

        if (isUpdated) {
            // Cập nhật thành công
            session.setAttribute("email", email);
            session.setAttribute("phone", phone);
            request.setAttribute("message", "Cập nhật thông tin thành công!");
        } else {
            // Lỗi khi cập nhật
            request.setAttribute("message", "Lỗi khi cập nhật thông tin.");
        }
        request.getRequestDispatcher("manageAccount.jsp").forward(request, response);
    }
}
