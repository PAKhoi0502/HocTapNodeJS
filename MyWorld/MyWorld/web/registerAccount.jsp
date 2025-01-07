<%-- 
    Document   : signin
    Created on : 3 thg 1, 2025, 13:39:07
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <h2>Create Account</h2>
            <form action="register" method="POST">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="confirm-password">Confirm Password:</label>
                    <input type="password" id="confirm-password" name="confirm-password" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="tel" id="phone" name="phone" required>
                </div>
                <button type="submit" class="btn">Register</button>
            </form>
            <div class="form-actions" style="margin-top: 15px;">
                <a href="login.jsp">Back to Login</a>
            </div>
            <div class="error-message">
                <% if (request.getAttribute("error") != null) { %>
                <p><%= request.getAttribute("error") %></p>
                <% } %>
            </div>
    </body>
</html>
