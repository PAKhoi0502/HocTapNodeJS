<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*, java.sql.*, jakarta.servlet.http.Part, DAL.UserDAO"%>
<%@page import="Model.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý tài khoản</title>
    </head>
    <body>
        <h1>Quản lý tài khoản</h1>
        <%
            // Kiểm tra xem người dùng đã đăng nhập chưa
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Lấy thông tin người dùng từ session
            int userId = (int) session.getAttribute("userId");
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserById(userId);

            // Nếu không tìm thấy thông tin người dùng, chuyển hướng về trang đăng nhập
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Gán các giá trị cần thiết
            String email = user.getEmail();
            String phone = user.getPhone();
        %>

        <!-- Form chỉnh sửa thông tin cá nhân -->
        <form method="post" action="updateAccount">
            <label for="username">Tên tài khoản:</label>
            <input type="text" id="username" name="username" value="<%= user.getName() %>" readonly><br><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= email %>" required><br><br>

            <label for="phone">Số điện thoại:</label>
            <input type="text" id="phone" name="phone" value="<%= phone %>" required><br><br>

            <button type="submit">Cập nhật thông tin</button>
        </form>
            
        <% if (request.getAttribute("message") != null) { %>
        <p style="color: red;"><%= request.getAttribute("message") %></p>
        <% } %>


        <br>

        <!-- Link đổi mật khẩu -->
        <a href="changePassword.jsp">Đổi mật khẩu</a>

        <br><br>

        <!-- Form cập nhật ảnh đại diện -->
        <form method="post" action="updateAvatar" enctype="multipart/form-data">
            <!-- Hiển thị ảnh đại diện hiện tại -->
            <p>Ảnh đại diện hiện tại:</p>
            <%
                // Kiểm tra người dùng đã đăng nhập chưa
                if (session.getAttribute("userId") == null) {
                    response.sendRedirect("login.jsp");
                    return;
                }

                // Lấy đường dẫn avatar
                String avatarPath = (String) session.getAttribute("avatarPath");
                if (avatarPath == null || avatarPath.isEmpty()) {
                    avatarPath = "avatars/default-avatar.png"; // Đường dẫn tới ảnh mặc định
                }

                // In ra đường dẫn avatar để debug
            %>

            <img src="<%= request.getContextPath() + "/" + avatarPath %>" alt="Avatar" width="150" height="150">



            <br><br>

            <!-- Chọn ảnh mới -->
            <label for="avatar">Chọn ảnh mới:</label>
            <input type="file" id="avatar" name="avatar" accept="image/*" required>
            <br><br>
            <button type="submit">Cập nhật ảnh</button>
        </form>

        <!-- Hiển thị thông báo nếu có -->

        <br>   <br>   <br>
        <a href="home.jsp">Back Home</a>
    </body>
</html>
