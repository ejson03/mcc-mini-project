const crypto = require("crypto");
const rand = require("csprng");
const mongoose = require("mongoose");
const gravatar = require("gravatar");
const user = require("./models");

exports.login = function (email, password, callback) {
  user.find({ email: email }, function (err, users) {
    if (users.length != 0) {
      const temp = users[0].salt;
      const hash_db = users[0].hashed_password;
      const id = users[0].token;
      const newpass = temp + password;
      const hashed_password = crypto
        .createHash("sha512")
        .update(newpass)
        .digest("hex");
      const grav_url = gravatar.url(email, { s: "200", r: "pg", d: "404" });
      if (hash_db == hashed_password) {
        callback({
          response: "Login Sucess",
          res: true,
          token: id,
          grav: grav_url,
        });
      } else {
        callback({ response: "Invalid Password", res: false });
      }
    } else {
      callback({ response: "User not exist", res: false });
    }
  });
};
