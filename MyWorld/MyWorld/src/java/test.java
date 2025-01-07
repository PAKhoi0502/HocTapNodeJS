import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class test {

    public static void main(String[] args) {
        // Thông tin tài khoản email
        final String username = "khoiphamvk123@gmail.com"; // Thay bằng email của bạn
        final String password = "qhyt dnux kfyn shtu";   // Thay bằng App Password (không phải mật khẩu thông thường)

        // Địa chỉ email người nhận
        String toEmail = "anhkhoivk8939l@gmail.com"; // Thay bằng email người nhận

        // Nội dung email
        String subject = "Test Email from Java";
        String messageText = "Chào bạn,\n\nĐây là một email thử nghiệm được gửi từ Java. \n\nThân ái!";

        // Cấu hình SMTP
        String host = "smtp.gmail.com";
        int port = 587; // TLS (STARTTLS)

Properties props = new Properties();
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");
props.put("mail.smtp.starttls.required", "true");
props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Bắt buộc dùng TLS 1.2
props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", "587");

Session session = Session.getInstance(props, new Authenticator() {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("khoiphamvk123@gmail.com", "qhyt dnux kfyn shtu");
    }
});

try {
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress("khoiphamvk123@gmail.com"));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("anhkhoivk8939@gmail.com"));
    message.setSubject("Test Email");
    message.setText("This is a test email sent from Java.");

    Transport.send(message);
    System.out.println("Email sent successfully!");
} catch (MessagingException e) {
    e.printStackTrace();
    System.err.println("Failed to send email: " + e.getMessage());
}

    }
}
