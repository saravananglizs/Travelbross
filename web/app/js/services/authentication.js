'use strict'

app.factory('Authentication', ['$http', '$q', function($http, $q) {
	var service = {};
	var urlToPost = "http://localhost:4567";

	service.sendHTTPRequest = function(endpoint, dataToPost) {
		var urlToPost = this.getUrlToPost(endpoint);
		this.setHTTPHeaders();
		var deferred = $q.defer();
		return $http({
			method: 'POST',
			url: urlToPost,
			headers: {'Content-type': 'application/x-www-form-urlencoded'},
			data: $.param(dataToPost)
		}).then(function(response) {
			deferred.resolve(response.data);
			return deferred.promise;
		}, function(error) {
			deferred.reject(error);
			return deferred.promise;
		});
	};

	service.sendRawHTTPRequest = function(endpoint, dataToPost) {
		var deferred = $q.defer();
		return $http({
			method: 'GET',
			url: urlToPost,
			headers: {'Content-type': 'x-www-form-urlencoded'},
			data: $.param(dataToPost)
		}).then(function(response) {
			deferred.resolve(response.data);
			return deferred.promise;
		}, function(error) {
			deferred.reject(error);
			return deferred.promise;
		});
	};

	service.getUrlToPost = function(endpoint) {
		return urlToPost + endpoint;
	};

	service.setHTTPHeaders = function() {
		$http.defaults.headers.common['Access-Control-Allow-Origin'] = "*";
		$http.defaults.headers.common['Access-Control-Allow-Method'] = "POST, GET, OPTIONS, DELETE, PUT";
		$http.defaults.headers.common['Access-Control-Max-Age'] = "1000";
		$http.defaults.headers.common['Access-Control-Allow-Headers'] = "X-Requested-with, Content-type, Origin, Authorization, Accept, Client-Security-Token, Accept-Encoding";
		$http.defaults.withCredentials = true;
		// $http.defaults.headers.common['Content-type'] = "application/x-www-form-urlencoded";
	};

	return service;

}]);