<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAL.UserDAO" %>
<%@page import="Model.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/manageAccCSS.css" rel="stylesheet" type="text/css"/>
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

            // Lấy avatar và thông tin cơ bản
            String avatarPath = (String) session.getAttribute("avatarPath");
            if (avatarPath == null || avatarPath.isEmpty()) {
                avatarPath = "avatars/default-avatar.png"; // Đường dẫn tới ảnh mặc định
            }
        %>

        <!-- Wrapper for two forms -->
        <div class="form-container">
            <!-- Form chỉnh sửa thông tin cá nhân -->
            <form method="post" action="updateAccount" class="edit-form">
                <h2>Cập nhật thông tin</h2>
                <label for="username">Tên tài khoản:</label>
                <input type="text" id="username" name="username" value="<%= user.getName() %>" readonly><br><br>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required><br><br>

                <label for="phone">Số điện thoại:</label>
                <input type="text" id="phone" name="phone" value="<%= user.getPhone() %>" required><br><br>

                <button type="submit">Cập nhật thông tin</button>
            </form>

            <!-- Form cập nhật ảnh đại diện -->
            <form method="post" action="updateAvatar" enctype="multipart/form-data" class="avatar-form">
                <h2>Cập nhật ảnh đại diện</h2>
                <p>Ảnh hiện tại:</p>
                <img src="<%= request.getContextPath() + "/" + avatarPath %>" alt="Avatar" width="150" height="150">
                <br><br>

                <label for="avatar">Chọn ảnh mới:</label>
                <input type="file" id="avatar" name="avatar" accept="image/*" required>
                <br><br>
                <button type="submit">Cập nhật ảnh</button>
            </form>
        </div>

        <% if (request.getAttribute("message") != null) { %>
        <p style="color: red;"><%= request.getAttribute("message") %></p>
        <% } %>

        <br><br>
        <a href="home.jsp">Trở về Trang Chủ</a>
    </body>
</html>
