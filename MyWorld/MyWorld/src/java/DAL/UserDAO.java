/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.util.ArrayList;
import java.util.List;
import Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.security.MessageDigest;

/**
 *
 * @author ASUS
 */
public class UserDAO extends DBContext {

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User u = new User(
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Avatar")
                );
                userList.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error in getAll: " + e.getMessage());
        }
        return userList;
    }

    // hàm kiểm tra thông tin từ người dùng nhập vào đến sql server
    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Avatar")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy người dùng
    }

    // lấy id
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("UserID"),
                        rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Avatar")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy người dùng
    }

// Kiểm tra xem thông tin có bị trùng lặp
    public String checkDuplicate(String username, String email, String phone) {
        String sql = "SELECT Username, Email, Phone FROM Users WHERE Username = ? OR Email = ? OR Phone = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, phone);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (username.equals(rs.getString("Username"))) {
                    return "Username";
                } else if (email.equals(rs.getString("Email"))) {
                    return "Email";
                } else if (phone.equals(rs.getString("Phone"))) {
                    return "Phone";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy trùng lặp
    }

    // Kiểm tra trùng lặp khi update thông tin tài khoản
    public String checkDuplicateForUpdate(int userId, String username, String email, String phone) {
        String sql = "SELECT Username, Email, Phone FROM Users WHERE (Username = ? OR Email = ? OR Phone = ?) AND UserID != ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, phone);
            st.setInt(4, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (username.equals(rs.getString("Username"))) {
                    return "Username";
                }
                if (email.equals(rs.getString("Email"))) {
                    return "Email";
                }
                if (phone.equals(rs.getString("Phone"))) {
                    return "Phone";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy trùng lặp
    }

    // lưu thông tin vào sql server khi đăng kí tài khoản
    public boolean insertUser(String username, String password, String email, String phone) {
        String sql = "INSERT INTO Users (Username, Password, Email, Phone, Avatar) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password); // Bạn nên hash mật khẩu ở đây nếu cần
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, "avatars/default-avatar.png"); // Sử dụng ảnh mặc định
            return st.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi để kiểm tra
        }
        return false; // Nếu có lỗi, trả về false
    }

    //Cập nhật thông tin
    public boolean updateUser(int userId, String username, String email, String phone) {
        String sql = "UPDATE Users SET Username = ?, Email = ?, Phone = ? WHERE UserID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, phone);
            st.setInt(4, userId);
            return st.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //đổi mật khẩu 
    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE UserID = ? AND Password = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newPassword); // Mật khẩu mới
            st.setInt(2, userId);
            st.setString(3, currentPassword); // Mật khẩu hiện tại
            return st.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Lỗi khi cập nhật
    }

//cập nhật ảnh
    public boolean updateAvatar(int userId, String avatarPath) {
        String sql = "UPDATE Users SET Avatar = ? WHERE UserID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, avatarPath); // Đường dẫn ảnh
            st.setInt(2, userId);
            return st.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Lấy mật khẩu từ tài khoản hoặc email
    public String getEmailByUsernameOrEmail(String input) {
        String sql = "SELECT Email FROM Users WHERE Username = ? OR Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, input);
            st.setString(2, input);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("Email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //lưu token
    public void savePasswordResetToken(String email, String token) {
        String sql = "UPDATE Users SET reset_token = ?, token_expiration = DATEADD(MINUTE, 30, GETDATE()) WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, token);
            st.setString(2, email);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //cập nhật mật khẩu với token
    public boolean updatePasswordWithToken(String token, String newPassword) {
        String sql = "UPDATE Users SET Password = ?, reset_token = NULL, token_expiration = NULL WHERE reset_token = ? AND token_expiration > GETDATE()";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newPassword); // Hash mật khẩu nếu cần
            st.setString(2, token);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //kiểm tra token
    public boolean isValidToken(String token) {
        String sql = "SELECT COUNT(*) FROM Users WHERE reset_token = ? AND token_expiration > GETDATE()";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // hàm mã hóa mật khẩu - Chưa sử dụng
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

}
