var fastability = require('../routes/Fastability');
module.exports = function(app){
	
	app.get('/Fastability/allEmpDetails',fastability.getAllEmployeesDetails);
	
}