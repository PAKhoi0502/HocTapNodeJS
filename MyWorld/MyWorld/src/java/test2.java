
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class test2 {

    public static void main(String[] args) {
        try {
            String username = System.getProperty("EMAIL_USERNAME");
            String password = System.getProperty("EMAIL_PASSWORD");

            String to = "anhkhoivk8939@example.com"; // Thay bằng email người nhận
            String subject = "Test Email";
            String content = "Đây là email thử nghiệm được gửi từ Java.";

            if (username == null || password == null) {
                System.err.println("Biến môi trường EMAIL_USERNAME hoặc EMAIL_PASSWORD không được đặt.");
                return;
            }

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
