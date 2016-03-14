var MongoClient = require('mongodb').MongoClient,
	Server = require('mongodb').Server,
	db;
var mongoClient = new MongoClient(new Server('localhost',27017));
mongoClient.open(function(err,mongoClient){
	db=mongoClient.db("Fastability");

});



exports.getAllEmployeesDetails = function(req,res){
	var collection = db.collection("employees");
	 
    var collection_rtsw = db.collection("rtswEmp");
    var collection_ae = db.collection("aeEmp");
    var collection_mp = db.collection("mpEmp");
    
    var result_Collection = db.collection("EmployeeDetails");
    result_Collection.remove({},function(err,col){});
    
    var map = function() {
        //emit(this.employee_id,{Name:this.case_id,Score:0});
        emit(this.employee_id,{FastabilityId:this.case_id,Name:this.full_name,EmployeeId:this.employee_id,EmployerId:this.employer_id,Age:this.age,Gender:this.gender,PhoneNumber:this.phone,EmailId:this.email,Role:this.designation,CompanyId:this.company_id,Injury:this.injury,Limitation:this.limitation,ProgramType:this.program_type,ProgramDuration:this.program_duration,SaveByReturn:this.save_by_return,ResourceCriticality:this.resource_criticality,Status:this.status,ProgressInDays:this.progress_in_days,Pic:this.pic,RTSWDetails:{WeeklyProgress:[],OverallScore:0,PrescriptionCompliance:0,ExerciseCompliance:0,ExerciseDetails:[],ExerciseEfficiency:0,ExerciseAccuracy:0,NonComplianceReasons:[]},EmployerDetails:{EmployerId:0,EmployerStatus:"",EmployerComments:""},ModificationOptions:[],EmployeeDegree:"",EmployeeExperience:"",EmployeeSkillSet:[],SplRequirement:"",Jobs:[]});
    }
    var map_rtsw = function() {
        //emit(this.employee_id,{Name:"",Score:this.progress.overall_score});
        
        var nonComplianceReasons = [];
        for(pnc=0;pnc<this.progress.prescription_compliance.noncompliance_reasons.length;pnc++){
        	nonComplianceReasons.push(this.progress.prescription_compliance.noncompliance_reasons[pnc]);
        }
        for(enc=0;enc<this.progress.exercise_compliance.noncompliance_reasons.length;enc++){
        	nonComplianceReasons.push(this.progress.exercise_compliance.noncompliance_reasons[enc]);
        }
        
        emit(this.employee_id,{FastabilityId:"",Name:"",EmployeeId:0,EmployerId:0,Age:"",Gender:"",PhoneNumber:"",EmailId:"",Role:"",CompanyId:"",Injury:"",Limitation:"",ProgramType:"",ProgramDuration:0,SaveByReturn:0,ResourceCriticality:"",Status:"",ProgressInDays:0,Pic:"",RTSWDetails:{WeeklyProgress:this.progress.weekly_progress,OverallScore:this.progress.overall_score,PrescriptionCompliance:this.progress.prescription_compliance.score,ExerciseCompliance:this.progress.exercise_compliance.score,ExerciseDetails:this.progress.exercise_compliance.exercise_details,ExerciseEfficiency:this.progress.exercise_compliance.overall_efficiency,ExerciseAccuracy:this.progress.exercise_compliance.overall_accuracy,NonComplianceReasons:nonComplianceReasons},EmployerDetails:{EmployerId:0,EmployerStatus:"",EmployerComments:""},ModificationOptions:[],EmployeeDegree:"",EmployeeExperience:"",EmployeeSkillSet:[],SplRequirement:"",Jobs:[]});
    }
    
    var map_mp = function() {
    	emit(this.employee_id,{FastabilityId:"",Name:"",EmployeeId:0,EmployerId:0,Age:"",Gender:"",PhoneNumber:"",EmailId:"",Role:"",CompanyId:"",Injury:"",Limitation:"",ProgramType:"",ProgramDuration:0,SaveByReturn:0,ResourceCriticality:"",Status:"",ProgressInDays:0,Pic:"",RTSWDetails:{WeeklyProgress:[],OverallScore:0,PrescriptionCompliance:0,ExerciseCompliance:0,ExerciseDetails:[],ExerciseEfficiency:0,ExerciseAccuracy:0,NonComplianceReasons:[]},EmployerDetails:{EmployerId:this.employer_id,EmployerStatus:this.employer_status,EmployerComments:this.comments},ModificationOptions:this.modifications_suggested,EmployeeDegree:"",EmployeeExperience:"",EmployeeSkillSet:[],SplRequirement:"",Jobs:[]});
    
    }
    
    var map_ae = function(){	  	
    	emit(this.employee_id,{FastabilityId:"",Name:"",EmployeeId:0,EmployerId:0,Age:"",Gender:"",PhoneNumber:"",EmailId:"",Role:"",CompanyId:"",Injury:"",Limitation:"",ProgramType:"",ProgramDuration:0,SaveByReturn:0,ResourceCriticality:"",Status:"",ProgressInDays:0,Pic:"",RTSWDetails:{WeeklyProgress:[],OverallScore:0,PrescriptionCompliance:0,ExerciseCompliance:0,ExerciseDetails:[],ExerciseEfficiency:0,ExerciseAccuracy:0,NonComplianceReasons:[]},EmployerDetails:{EmployerId:0,EmployerStatus:"",EmployerComments:""},ModificationOptions:[],EmployeeDegree:this.degree,EmployeeExperience:this.experience,EmployeeSkillSet:this.skillset,SplRequirement:this.special_requirement,Jobs:this.jobs});
    }
    

    var reduce = function(key, value) {
      //var result = {Name:"",Score:0};
      var result = {FastabilityId:"",Name:"",EmployeeId:0,EmployerId:0,Age:"",Gender:"",PhoneNumber:"",EmailId:"",Role:"",CompanyId:"",Injury:"",Limitation:"",ProgramType:"",ProgramDuration:0,SaveByReturn:0,ResourceCriticality:"",Status:"",ProgressInDays:0,Pic:"",RTSWDetails:{WeeklyProgress:[],OverallScore:0,PrescriptionCompliance:0,ExerciseCompliance:0,ExerciseDetails:[],ExerciseEfficiency:0,ExerciseAccuracy:0,NonComplianceReasons:[]},EmployerDetails:{EmployerId:0,EmployerStatus:"",EmployerComments:""},ModificationOptions:[],EmployeeDegree:"",EmployeeExperience:"",EmployeeSkillSet:[],SplRequirement:"",Jobs:[]};
      
	  for(i=0;i<value.length;i++){
	  	
	  	if(result.FastabilityId==="" && value[i].FastabilityId!==null){
	  	result.FastabilityId = value[i].FastabilityId;}
	  
	  	if(result.Name==="" && value[i].Name!==null){
	  	result.Name = value[i].Name;}
	  	
	  	if(result.EmployeeId===0 && value[i].EmployeeId!== null){
		  	result.EmployeeId=value[i].EmployeeId;}
		  	
		if(result.EmployerId===0 && value[i].EmployerId!== null){
		  	result.EmployerId=value[i].EmployerId;}
	  	
	  	if(result.Age==="" && value[i].Age!==null){
	  	result.Age = value[i].Age;}
	  	
	  	if(result.Gender==="" && value[i].Gender!==null){
	  	result.Gender = value[i].Gender;}
	  	
	  	if(result.PhoneNumber==="" && value[i].PhoneNumber!==null){
	  	result.PhoneNumber = value[i].PhoneNumber;}
	  	
	    if(result.EmailId==="" && value[i].EmailId!==null){
	  	result.EmailId = value[i].EmailId;}
	  	
	  	 if(result.Role==="" && value[i].Role!==null){
	  	result.Role = value[i].Role;}
	  	
	  	if(result.CompanyId==="" && value[i].CompanyId!==null){
	  	result.CompanyId = value[i].CompanyId;}
		
		if(result.Injury==="" && value[i].Injury!==null){
	  	result.Injury = value[i].Injury;}
	  	
	  	if(result.Limitation==="" && value[i].Limitation!==null){
	  	result.Limitation = value[i].Limitation;}
		  	
		if(result.ProgramType==="" && value[i].ProgramType!==null){
	  	result.ProgramType = value[i].ProgramType;}
		
		if(result.ProgramDuration===0 && value[i].ProgramDuration!== null){
		  result.ProgramDuration=value[i].ProgramDuration;}
		  
		if(result.SaveByReturn===0 && value[i].SaveByReturn!== null){
		  result.SaveByReturn=value[i].SaveByReturn;}
	  	
		if(result.ResourceCriticality==="" && value[i].ResourceCriticality!==null){
	  	result.ResourceCriticality = value[i].ResourceCriticality;}
		
	  	if(result.Status==="" && value[i].Status!==null){
	  	result.Status = value[i].Status;}
		
		if(result.ProgressInDays===0 && value[i].ProgressInDays!== null){
		  	result.ProgressInDays=value[i].ProgressInDays;}
		
		if(result.Pic==="" && value[i].Pic!==null){
	  	result.Pic = value[i].Pic;}
	  	
	  	
	  	if(value[i].RTSWDetails.WeeklyProgress.length>0){
	  		for(j=0;j<value[i].RTSWDetails.WeeklyProgress.length;j++){
	  			result.RTSWDetails.WeeklyProgress.push(value[i].RTSWDetails.WeeklyProgress[j]);
	  		}
	  	}
	  	
	  	
	  	if(result.RTSWDetails.OverallScore===0 && value[i].RTSWDetails.OverallScore!== null){
		  	result.RTSWDetails.OverallScore=value[i].RTSWDetails.OverallScore;}
		  	
		if(result.RTSWDetails.PrescriptionCompliance===0 && value[i].RTSWDetails.PrescriptionCompliance!== null){
		  	result.RTSWDetails.PrescriptionCompliance=value[i].RTSWDetails.PrescriptionCompliance;}
		  	
		if(result.RTSWDetails.ExerciseCompliance===0 && value[i].RTSWDetails.ExerciseCompliance!== null){
		  	result.RTSWDetails.ExerciseCompliance=value[i].RTSWDetails.ExerciseCompliance;}
		  	
		if(result.RTSWDetails.ExerciseEfficiency===0 && value[i].RTSWDetails.ExerciseEfficiency!== null){
		  	result.RTSWDetails.ExerciseEfficiency=value[i].RTSWDetails.ExerciseEfficiency;}
		  	
		if(result.RTSWDetails.ExerciseAccuracy===0 && value[i].RTSWDetails.ExerciseAccuracy!== null){
		  	result.RTSWDetails.ExerciseAccuracy=value[i].RTSWDetails.ExerciseAccuracy;}
		
		if(result.EmployerDetails.EmployerId===0 && value[i].EmployerDetails.EmployerId!== null){
		  	result.EmployerDetails.EmployerId=value[i].EmployerDetails.EmployerId;}
		
		
		if(result.EmployerDetails.EmployerStatus==="" && value[i].EmployerDetails.EmployerStatus!==null){
	  	result.EmployerDetails.EmployerStatus = value[i].EmployerDetails.EmployerStatus;}
	  	
	  	if(result.EmployerDetails.EmployerComments==="" && value[i].EmployerDetails.EmployerComments!==null){
	  	result.EmployerDetails.EmployerComments = value[i].EmployerDetails.EmployerComments;}
	  	
	  	
	  	if(value[i].ModificationOptions.length>0){
	  		for(mpo=0;mpo<value[i].ModificationOptions.length;mpo++){
	  			result.ModificationOptions.push(value[i].ModificationOptions[mpo]);
	  		}
	  	}
		
		if(value[i].RTSWDetails.ExerciseDetails.length>0){
	  		for(d=0;d<value[i].RTSWDetails.ExerciseDetails.length;d++){
	  			result.RTSWDetails.ExerciseDetails.push(value[i].RTSWDetails.ExerciseDetails[d]);
	  		}
	  	}
	  	
	  	if(value[i].RTSWDetails.NonComplianceReasons.length>0){
	  		for(n=0;n<value[i].RTSWDetails.NonComplianceReasons.length;n++){
	  			result.RTSWDetails.NonComplianceReasons.push(value[i].RTSWDetails.NonComplianceReasons[n]);
	  		}
	  	}
	  	
	  	if(value[i].EmployeeSkillSet.length>0){
	  		for(ess=0;ess<value[i].EmployeeSkillSet.length;ess++){
	  			result.EmployeeSkillSet.push(value[i].EmployeeSkillSet[ess]);
	  		}
	  	}
	  	
	  	if(value[i].Jobs.length>0){
	  		for( jo=0;jo<value[i].Jobs.length;jo++){
	  			result.Jobs.push(value[i].Jobs[jo]);
	  		}
	  	}
	  	
	  
	  	
	  	if(result.EmployeeDegree==="" && value[i].EmployeeDegree!==null){
	  	result.EmployeeDegree = value[i].EmployeeDegree;}
		  	
		if(result.EmployeeExperience==="" && value[i].EmployeeExperience!==null){
	  	result.EmployeeExperience = value[i].EmployeeExperience;}
	  	
	  	if(result.SplRequirement==="" && value[i].SplRequirement!==null){
	  	result.SplRequirement = value[i].SplRequirement;}	
      }
      return result;
    }
     var options = {out: {reduce:"EmployeeDetails"}};
     
    var joinFunction = function(){
    	collection_rtsw.mapReduce(map_rtsw, reduce, options, function (err, col) {
    		mpJoinFunction();	
    	});
    }
    
     var mpJoinFunction = function(){
    	collection_mp.mapReduce(map_mp, reduce, options, function (err, col) {
    		aeJoinFunction();
    	});
    }
    
    var aeJoinFunction = function(){
    	collection_ae.mapReduce(map_ae, reduce, options, function (err, col) {
    	col.find(function (err, cursor) {
        	cursor.toArray(function (err, results) {
                	res.send(results);
            	});            
   		});
    });
    }

    collection.mapReduce(map, reduce, options, function (err, collection) {
    	joinFunction();
    });
    

};


