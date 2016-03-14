var socialPrism = require('../routes/SocialPrism');
module.exports = function(app){
	
	app.get('/SocialPrism/getAllTweets',socialPrism.getAllTweets);
	
}