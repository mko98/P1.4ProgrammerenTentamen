/**
 * Created by Max on 13-6-2017.
 */

var mysql = require('mysql');
var config = require('../config');

var pool2 = mysql.createPool({
    multipleStatements:true,
    connectionLimit : 25,
    host : config.dbHost,
    user : config.dbUsername,
    password : config.dbPassword,
    database : config.dbDatabaseUser

});

module.exports = pool2;