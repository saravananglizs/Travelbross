'use strict'

app.factory('UtilService', function() {
	var service = {};

	service.getTimezoneName = function() {
		var tz = jstz.determine();
		return tz.name();
	};

	service.getTimezoneOffset = function() {
		var offset =  new Date().getTimezoneOffset();
		offset = ((offset < 0 ? '+':'-') + 
		service.pad(parseInt(Math.abs(offset/60), 2)) + ":" +
		service.pad(Math.abs(offset%60), 2));

		return "GMT" + offset;
	};

	service.pad = function(number, length) {
		var str = "" + number;
		while(str.length < length) {
			str = '0' + str;
		}
		return str;
	};

	return service;

});