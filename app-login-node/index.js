const express  = require('express');
const app      = express();
const port     = process.env.PORT || 8080;
const bodyParser = require("body-parser")
// Configuration
app.use(express.static(__dirname + '/public'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Routes

require('./routes.js')(app);

app.listen(port);

console.log('The App runs on port ' + port);