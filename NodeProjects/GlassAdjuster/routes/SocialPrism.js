var MongoClient = require('mongodb').MongoClient,
	Server = require('mongodb').Server,
	db;

var mongoClient = new MongoClient(new Server('localhost',27017));
mongoClient.open(function(err,mongoClient){
	db=mongoClient.db("tweet");

});





exports.getAllTweets= function(req,res){
	db.collection('tweets',function(err,collection){
		collection.find().toArray(function(err,items){
			res.jsonp(items);
		});
	});
	
};
