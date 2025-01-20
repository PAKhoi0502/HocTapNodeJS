import { resolveInclude } from "ejs";
import db from "../models/index"
import bcrypt from 'bcryptjs';

let handleUserLogin = (email, password) => { // check email pass trong db
    return new Promise(async (resolve, reject) => {
        try {
            let userData = {};

            let isExist = await checkUserMail(email);

            if (isExist) {

                let user = await db.User.findOne({ //lấy pass từ db để so sánh với hash pass
                    attributes: ['email', 'roleId', 'password'], //lấy infor từ db
                    where: { email: email },
                    raw: true // user trả về object
                });

                if (user) { // nếu user tồn tại

                    // let check = await bcrypt.compareSync(password, user.password);
                    let check = true;
                    if (check) {
                        userData.errCode = 0,
                            userData.errMessage = 'Done';
                        delete user.password;
                        userData.user = user;
                    } else {
                        userData.errCode = 3,
                            userData.errMessage = 'Wrong Password';
                    }
                }

                else {
                    userData.errCode = 2;
                    userData.errMessage = "User's Email isn't exist in your system. Pls try other email"
                }
            } else {
                userData.errCode = 1;
                userData.errMessage = "Your's Email isn't exist in your system. Pls try other email!";
            }
            resolve(userData);

        } catch (e) {
            reject(e);
        }
    });
};


let checkUserMail = (userEmail) => {
    return new Promise(async (resolve, reject) => {
        try {
            let user = await db.User.findOne({
                where: { email: userEmail }
            });
            if (user) {
                resolve(true);
            } else {
                resolve(false);
            };
        } catch (e) {
            reject(e);
        }
    });
}

module.exports = {
    handleUserLogin: handleUserLogin,
}