import userServices from "../services/userService";

let handleLogin = async (req, res) => {
    console.log("Login API called. Request body:", req.body); // In dữ liệu từ fronten
    let email = req.body.email;
    let password = req.body.password;

    if (!email || !password) { //check email password
        return res.status(500).json({
            errCode: 1,
            message: 'Missing inputs parameter!',
        });
    }

    let userData = await userServices.handleUserLogin(email, password);
    console.log(userData);

    return res.status(200).json({
        errCode: userData.errCode,
        message: userData.errMessage,
        user: userData.user ? userData.user : {}
    });
}

module.exports = {
    handleLogin: handleLogin,
}