<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="CSS/resetPassCSS.css" rel="stylesheet" type="text/css"/>
        <title>Reset Password</title>
    </head>
    <body>
        <form method="post" action="resetPassword">
            <input type="hidden" name="token" value="<%= request.getParameter("token") %>">
            <label for="newPassword">Mật khẩu mới:</label><br>
            <input type="password" id="newPassword" name="newPassword" required><br><br>

            <label for="confirmPassword">Xác nhận mật khẩu mới:</label><br>
            <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>

            <button type="submit">Đặt lại mật khẩu</button>
            <%
String message = (String) request.getAttribute("message");
if (message != null) {
            %>
            <p style="color: red;"><%= message %></p>
            <%
                }
            %>
        </form>
    </body>
</html>
