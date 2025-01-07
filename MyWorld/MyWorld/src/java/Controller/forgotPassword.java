package Controller;

import DAL.UserDAO;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "forgotPassword", urlPatterns = {"/forgotPassword"})
public class forgotPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String emailOrUsername = request.getParameter("emailOrUsername");

        if (emailOrUsername == null || emailOrUsername.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng nhập email hoặc tên tài khoản.");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }

        System.out.println("Received forgot password request for: " + emailOrUsername);

        // Kiểm tra email hoặc username có tồn tại không
        UserDAO userDAO = new UserDAO();
        String userEmail = userDAO.getEmailByUsernameOrEmail(emailOrUsername.trim());

        if (userEmail != null) {
            System.out.println("Found email associated with username/email: " + userEmail);

            // Tạo mã token
            String token = UUID.randomUUID().toString();
            System.out.println("Generated token: " + token);

            // Lưu token vào database
            userDAO.savePasswordResetToken(userEmail, token);
            System.out.println("Saved token into database for email: " + userEmail);

            // Tạo liên kết đặt lại mật khẩu
            String resetLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/resetPassword?token=" + token;

            System.out.println("Generated reset link: " + resetLink);

            // Gửi email cho người dùng
            try {
                sendEmail(userEmail, "Khôi phục mật khẩu", "Nhấn vào đây để đặt lại mật khẩu: " + resetLink);
                request.setAttribute("message", "Hãy kiểm tra email của bạn để đặt lại mật khẩu.");
            } catch (Exception e) {
                request.setAttribute("message", "Đã xảy ra lỗi khi gửi email. Vui lòng thử lại sau.");
                e.printStackTrace(); // In chi tiết lỗi
                System.err.println("Lỗi khi gửi email: " + e.getMessage());
            }
        } else {
            System.out.println("No email found for username/email: " + emailOrUsername);

            // Thông báo không tìm thấy người dùng
            request.setAttribute("message", "Email hoặc tài khoản không tồn tại!");
        }

        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
    }

    public void sendEmail(String to, String subject, String content) throws MessagingException {
        // Lấy thông tin email và mật khẩu từ biến môi trường
        final String username = System.getenv("EMAIL_USERNAME"); // Đặt biến môi trường EMAIL_USERNAME
        final String password = System.getenv("EMAIL_PASSWORD"); // Đặt biến môi trường EMAIL_PASSWORD
        String host = "smtp.gmail.com";

        if (username == null || password == null) {
            throw new MessagingException("Thông tin đăng nhập email không được cấu hình đúng.");
        }

        // Cấu hình Properties cho JavaMail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Tạo phiên làm việc với Gmail SMTP
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Tạo email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(content, "text/plain; charset=UTF-8");

        // Gửi email
        Transport.send(message);
        System.out.println("Email sent successfully to " + to);
        System.out.println("EMAIL_USERNAME: " + System.getenv("EMAIL_USERNAME"));
        System.out.println("EMAIL_PASSWORD: " + System.getenv("EMAIL_PASSWORD"));

    }
}
