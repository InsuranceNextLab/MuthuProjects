var mysql = require('mysql');
//var fs = require('node-fs');
var fs = require('fs.extra');
var path = require("path");
var connection = mysql.createConnection({
  host: 'localhost',
  user: 'inextusr',
  password: 'inextpwd',
  database: 'claim_adjustment'
});
connection.connect();
exports.getClaimDetails = function(req, res) {

  connection.query("select * from claim_details where is_active = 1 and claim_number = '" + req.params.claim_id + "';", function(error, rows, fields) {
    if (!error) {
      res.end(JSON.stringify(rows));
      console.log('The solution is: ', rows);
    } else {
      console.log('Error while performing Query.');

    }
  });
  console.log(req.params.claim_id);
  //connection.end();
};

exports.addClaimDetails = function(req, res) {
  //var input = JSON.parse(JSON.stringify(req.data));
  var claim_details = JSON.parse(req.params.data);
  console.log(claim_details.claim_number + "PODA");
  var data = {

    claim_number: claim_details.claim_number,
    claimant_name: claim_details.claimant_name,
    insured_location: claim_details.insured_location,
    adjuster_name: claim_details.adjuster_name,
    claim_status: claim_details.claim_status,
    loss_date: claim_details.loss_date,
    survey_date: claim_details.survey_date,
    claim_extend_damage: claim_details.extend_demage,
    roof_type: claim_details.roof_type,
    cause_of_loss: claim_details.cause_ofloss,
    is_active: 1,
    is_image: claim_details.is_image,
    is_video: claim_details.is_video,
    is_audio:claim_details.is_audio,
    file_path: "http://104.196.43.212/ClaimAdjuster/" + claim_details.claim_number + "/",
    is_created_date: "afasf"

  };
  var is_check = isActiveCheck();
  if (is_check) {
    console.log("IsCheck = " + is_check);
    connection.query('SELECT * FROM claim_details WHERE claim_number = ?', [claim_details.claim_number], function(err, rows, fields) {
      if (err) throw err;
      if (rows.length == 0) {
        connection.query("INSERT INTO claim_details set ? ", data, function(err, rows) {
          if (err) throw err;
          if (rows.affectedRows > 0) {
            console.log("Inserted Rows Affected = " + rows.affectedRows);
			res.end("Inserted Rows Affected = " + rows.affectedRows);
          }

        });

      } else {
        connection.query("UPDATE claim_details set ? WHERE claim_number = ? ", [data, claim_details.claim_number], function(err, rows, fields) {
          if (err) throw err;
          if (rows.affectedRows > 0) {
            console.log("Updated Full Details Rows Affected = " + rows.affectedRows);
			res.end("Updated Full Details Rows Affected = " + rows.affectedRows);
          }
        });

      }

    });
  }

}

function isActiveCheck() {
  connection.query('SELECT * FROM claim_details WHERE is_active = ?', 1, function(err, rows, fields) {
    if (err) throw err;

    if (rows.length == 0) {
      console.log("Return");
      return true;
    } else {
      for (var i in rows) {
        console.log('Post Titles: ', rows[i].claim_number);
        var data = {
          is_active: 0
        };
        connection.query("UPDATE claim_details set ? WHERE claim_number = ? ", [data, rows[i].claim_number], function(err, rows, fields) {
          if (err) throw err;
          if (rows.affectedRows > 0) {
            console.log("Rows Affected = " + rows.affectedRows);
          }
        });
      }
      //  i++;
      //  connection.end();
      return true;
    }
  });
  return true;
}

exports.proofUplods = function(req, res) {
 
  var tmp_path = req.files.thumbnail.path;
  var dir = '/var/lib/tomcat7/webapps/ClaimAdjuster';
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir);
  }
 
 //var file_name = req.files.thumbnail.name.substr(0, req.files.thumbnail.name.lastIndexOf('.'));
 var extension = path.extname(req.files.thumbnail.name);
 console.log(req.files.thumbnail.path);
 var file = path.basename(req.files.thumbnail.name,extension);
 var originl_filename = path.basename(req.files.thumbnail.name);
  var new_folderpath = dir + '/' + file;
  fs.exists(new_folderpath, function(exists) {
    if (!exists) {
      fs.mkdirSync(dir + '/' + file);
    }
  });
  // set where the file should actually exists - in this case it is in the "images" directory
  var target_path = dir + '/' + file + "/" + originl_filename;
  if (fs.existsSync(target_path)) {
    fs.unlink(target_path, function(err) {
      if (err) throw err;
      console.log("Exist File is Deleted");
    });
  }
    // move the file from the temporary location to the intended location
    fs.move(tmp_path, target_path, function(err) {
      if (err) throw err;
      // delete the temporary file, so that the explicitly set temporary upload dir does not get filled with unwanted files
      fs.unlink(tmp_path, function() {
        if (err) throw err;
        res.send('File uploaded to: ' + target_path + ' - ' + req.files.thumbnail.size + ' bytes');
      });
    });



}
