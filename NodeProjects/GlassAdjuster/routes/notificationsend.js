exports.sendNotification = function(req,res) {
  var headers = {
    "Content-Type": "application/json",
    "Authorization": "Basic OGU5ZmE1N2ItZjI3Yi00Mjc4LTlkYzYtY2U5NmI1MWQyNTg2"
  };
  var data = { 
  app_id: "63909b05-810f-4e65-b833-3717614dc99f",
  contents: {"en": req.params.message},
 chrome_web_icon:"http://173.255.115.46/SMB/img/climb.png",
  included_segments: ["All"]
  };
  var options = {
    host: "onesignal.com",
    port: 443,
    path: "/api/v1/notifications",
    method: "POST",
    headers: headers
  };
  
  var https = require('https');
  var req = https.request(options, function(res) {  
    res.on('data', function(data) {
      console.log("Response:");
      console.log(JSON.parse(data));
    });
  });
  
  req.on('error', function(e) {
    console.log("ERROR:");
    console.log(e);
  });
  
  req.write(JSON.stringify(data));
  req.end();
};

