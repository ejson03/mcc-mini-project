const crypto = require("crypto");
const rand = require("csprng");
const mongoose = require("mongoose");
const user = require("./models");

exports.register = function (email, password, callback) {
  const x = email;
  if (!(x.indexOf("@") == x.length)) {
    if (
      password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/) &&
      password.length > 4 &&
      password.match(/[0-9]/) &&
      password.match(/.[!,@,#,$,%,^,&,*,?,_,~]/)
    ) {
      const temp = rand(160, 36);
      const newpass = temp + password;
      const token = crypto
        .createHash("sha512")
        .update(email + rand)
        .digest("hex");
      const hashed_password = crypto
        .createHash("sha512")
        .update(newpass)
        .digest("hex");

      const newuser = new user({
        token: token,
        email: email,
        hashed_password: hashed_password,
        salt: temp,
      });

      user.find({ email: email }, function (err, users) {
        const len = users.length;

        if (len == 0) {
          newuser.save(function (err) {
            callback({ response: "Sucessfully Registered" });
          });
        } else {
          callback({ response: "Email already Registered" });
        }
      });
    } else {
      callback({ response: "Password Weak" });
    }
  } else {
    callback({ response: "Email Not Valid" });
  }
};
