var claimdetails = require('../routes/ClaimAdjustment');
var multipart = require('connect-multiparty');
var multipartMiddleware = multipart();
module.exports = function(app) {

  app.get('/ClaimAdjustment/getClaimDetails/:claim_id', claimdetails.getClaimDetails);
  app.get('/ClaimAdjustment/addClaimDetails/:data', claimdetails.addClaimDetails);
  app.post('/ClaimAdjustment/uploadProofs',multipartMiddleware, claimdetails.proofUplods);
  app.get('/', function(req, res) {
    res.sendfile("index.html");
  });

}
