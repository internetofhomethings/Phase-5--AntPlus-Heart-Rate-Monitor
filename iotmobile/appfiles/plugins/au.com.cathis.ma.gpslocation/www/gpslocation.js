var exec = require('cordova/exec'),
    cordova = require('cordova');

var gpslocation = {
  getCurrentPosition : function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback,’GPSLocation’, ‘getCurrentPosition’, []);
  }
}

module.exports = gpslocation;