var express= require('express');
var http = require('http');
var fs = require('node-fs');
var socketIO = require("socket.io");
var app = express();

var controllers_path = __dirname + '/controllers'
  , controller_files = fs.readdirSync(controllers_path)
controller_files.forEach(function (file) {
  require(controllers_path+'/'+file)(app)
});

var server = http.createServer(app).listen(9080);

var io = socketIO.listen(server);
//socket io settings 
require('./config/socketioOptions')(io);
console.info("Listening to port 9080");