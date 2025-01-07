<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
    </head>
    <body>
        <form method="post" action="forgotPassword">
            <label for="email">Nhập email hoặc tên tài khoản:</label><br>
            <input type="text" id="email" name="emailOrUsername" required><br><br>
            <button type="submit">Gửi yêu cầu</button>
        </form>
        <%
    String message = (String) request.getAttribute("message");
    if (message != null) {
        %>
        <p style="color: green;"><%= message %></p>
        <%
            }
        %>
    </body>
</html>
