<%-- 
    Document   : changePassword
    Created on : 3 thg 1, 2025, 16:32:22
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/changePassCSS.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Thay đổi mật khẩu</h1>

        <form method="post" action="changePassword">
            <label for="currentPassword">Mật khẩu hiện tại:</label>
            <input type="password" id="currentPassword" name="currentPassword" required><br><br>

            <label for="newPassword">Mật khẩu mới:</label>
            <input type="password" id="newPassword" name="newPassword" required><br><br>

            <label for="confirmPassword">Xác nhận mật khẩu mới:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>

            <button type="submit">Thay đổi mật khẩu</button>
        </form>

        <a href="home.jsp">Back Home</a>
        <%-- Hiển thị thông báo lỗi hoặc thành công nếu có --%>
        <% if (request.getAttribute("message") != null) { %>
        <p style="color: red;"><%= request.getAttribute("message") %></p>
        <% } %>
    </body>
</html>
