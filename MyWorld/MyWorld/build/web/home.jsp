<%-- 
    Document   : home
    Created on : 3 thg 1, 2025, 13:45:11
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <a href="home.jsp">Trang Chủ</a><br><br>
        <a href="update.jsp">Update</a><br><br>
        <a href="search.jsp">Tìm Kiếm</a><br><br>
        <img src="<%= session.getAttribute("avatarPath") %>" alt="Avatar" width="100" height="100">
        <div class="account-menu">
            <button onclick="toggleDropdown()">Tài Khoản</button>
            <div id="dropdown-menu" style="display: none;">
                <a href="manageAccount.jsp">Quản lý tài khoản</a><br>
                <a href="login.jsp">Đăng Xuất</a>
            </div>
        </div>
        <script>
            function toggleDropdown() {
                const dropdown = document.getElementById('dropdown-menu');
                if (dropdown.style.display === 'none') {
                    dropdown.style.display = 'block';
                } else {
                    dropdown.style.display = 'none';
                }
            }

            // Đóng menu khi click ra ngoài
            window.onclick = function (event) {
                const dropdown = document.getElementById('dropdown-menu');
                const button = event.target.closest('.account-menu');
                if (!button) {
                    dropdown.style.display = 'none';
                }
            };
        </script>
    </body>
</html>
