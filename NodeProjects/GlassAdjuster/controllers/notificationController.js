var notification = require('../routes/notificationsend');
module.exports = function(app){
	
	app.get('/Notification/sendnotification/:message',notification.sendNotification);
	
}