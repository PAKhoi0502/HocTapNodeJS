package Controller;


import DAL.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@MultipartConfig // Để hỗ trợ upload file
@WebServlet(name = "updateAvatar", urlPatterns = {"/updateAvatar"})
public class updateAvatar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object userIdObj = session.getAttribute("userId");

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (userIdObj == null) {
            request.setAttribute("message", "Vui lòng đăng nhập để tiếp tục.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        int userId = Integer.parseInt(userIdObj.toString());

        // Đường dẫn lưu trữ ảnh
        String savePath = getServletContext().getRealPath("/") + "avatars";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir(); // Tạo thư mục nếu chưa tồn tại
        }

        // Lấy thông tin file upload
        Part filePart = request.getPart("avatar");
        String fileName = filePart.getSubmittedFileName();
        String avatarPath = "avatars/" + fileName;

        // Lưu tệp ảnh lên server
        filePart.write(savePath + File.separator + fileName);

        // Cập nhật thông tin avatar vào cơ sở dữ liệu
        UserDAO userDAO = new UserDAO();
        boolean isUpdated = userDAO.updateAvatar(userId, avatarPath);

        // Gửi kết quả về lại giao diện
        if (isUpdated) {
            session.setAttribute("avatarPath", avatarPath); // Cập nhật session
            request.setAttribute("message", "Ảnh đại diện được cập nhật thành công!");
        } else {
            request.setAttribute("message", "Lỗi khi cập nhật ảnh đại diện.");
        }

        // Chuyển hướng về trang quản lý tài khoản
        request.getRequestDispatcher("manageAccount.jsp").forward(request, response);
    }

}
