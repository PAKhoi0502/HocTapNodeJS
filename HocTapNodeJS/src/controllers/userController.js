import userSevices from "../services/userService";

let handleLogin = async (req, res) => {
    let email = req.body.email;
    let password = req.body.password;

    if (!email || !password) { //check email password
        return res.status(500).json({
            errCode: 1,
            message: 'Missing inputs parameter!',
            user: userData ? userData.user : {}
        });
    }

    let userData = await userSevices.handleUserLogin(email, password);


    return res.status(200).json({
        errCode: userData.errCode,
        message: userData.errMessage,
        userData
    });
}

module.exports = {
    handleLogin: handleLogin,
}