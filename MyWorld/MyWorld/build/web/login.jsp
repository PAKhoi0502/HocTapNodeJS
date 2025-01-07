<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/loginCSS.css" rel="stylesheet" type="text/css"/>
        <title>Login</title>
    </head>
    <body>
        <form action="login" method="post">
            <h2>Login</h2>
            <label for="username">Tài khoản:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Mật khẩu:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Đăng nhập</button>
            <a href="registerAccount.jsp">Tạo tài khoản</a>
            <a href="forgotPassword.jsp">Quên Mật Khẩu?</a>
            <div class="error-message">
                <% if (request.getAttribute("error") != null) { %>
                <p><%= request.getAttribute("error") %></p>
                <% } %>
            </div>
        </form>
    </body>
</html>
