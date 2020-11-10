const register = require("./regsiter");
const login = require("./login");

module.exports = function (app) {
  app.get("/", function (req, res) {
    res.end("Node-Android-Project");
  });

  app.post("/login", function (req, res) {
    const email = req.body.email;
    const password = req.body.password;

    login.login(email, password, function (found) {
      console.log(found);
      res.json(found);
    });
  });

  app.post("/register", function (req, res) {
    const email = req.body.email;
    const password = req.body.password;
    console.log(email, password)

    register.register(email, password, function (found) {
      console.log(found);
      res.json(found);
    });
  });
};
