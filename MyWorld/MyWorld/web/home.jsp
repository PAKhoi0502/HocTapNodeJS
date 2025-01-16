<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home</title>
        <link href="CSS/homeCSS.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <!-- Header -->
        <header>
            <!-- Logo -->
            <div class="logo">
                <img src="image/home/logo.png" alt="Logo">
            </div>


            <!-- Navigation Links -->
            <nav>
                <a href="home.jsp">Trang Chủ</a>
                <a href="update.jsp">Update</a>
                <a href="search.jsp">Tìm Kiếm</a>
            </nav>

            <!-- Avatar and Account -->
            <div class="account-section">
                <img 
                    src="<%= session.getAttribute("avatarPath") != null ? session.getAttribute("avatarPath") : "default-avatar.png" %>" 
                    alt="Avatar" 
                    width="50" 
                    height="50" 
                    class="avatar">
                <button onclick="toggleDropdown()">Tài Khoản</button>
                <div id="dropdown-menu" style="display: none;">
                    <a href="manageAccount.jsp">Quản lý tài khoản</a>
                    <a href="changePassword.jsp">Đổi mật khẩu</a>
                    <a href="login.jsp">Đăng Xuất</a>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main>
            <!-- Để trống phần này để bạn thêm nội dung sau -->
        </main>

        <!-- Footer -->
        <footer>
            <p>&copy; Bản Quyền MyWorld Thuộc Về Phạm Anh Khôi</p>
            <p>Liên Hệ Thông Qua Các Mạng Xã Hội Sau:</p>
            <div class="social-links">
                <a href="https://www.facebook.com/profile.php?id=100011353367350" target="_blank" class="facebook">
                    <i class="fab fa-facebook-f"></i> Facebook
                </a>
                <a href="https://www.instagram.com/pakhoi_523/" target="_blank" class="instagram">
                    <i class="fab fa-instagram"></i> Instagram
                </a>
                <a href="https://www.tiktok.com/@pakhoi_523?is_from_webapp=1&sender_device=pc" target="_blank" class="tiktok">
                    <i class="fab fa-tiktok"></i> TikTok
                </a>
            </div>
        </footer>

        <!-- JavaScript -->
        <script>
            function toggleDropdown() {
                const dropdown = document.getElementById('dropdown-menu');
                dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
            }

            // Đóng menu khi click ra ngoài
            window.onclick = function (event) {
                const dropdown = document.getElementById('dropdown-menu');
                const button = event.target.closest('.account-section');
                if (!button) {
                    dropdown.style.display = 'none';
                }
            };
        </script>
    </body>
</html>
