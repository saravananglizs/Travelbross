'use strict'

//signup controller

app.controller('AuthenticationController', ['$scope','$state', '$ngBootbox', 'Authentication', 'UtilService', function($scope, $state, $ngBootbox, Authentication, UtilService) {
	// $scope.user = {};
	$scope.signup_progress = false;
	$scope.doSignup = function() {
		var password = $scope.user.password;
		var confirmPassword = $scope.user.confirm_password;
		if(password.trim() == confirmPassword.trim()) {
			$scope.signup_progress = true;
			var hash = CryptoJS.MD5($scope.user.password).toString();
			var timezoneName = UtilService.getTimezoneName();
			var timezoneOffset = UtilService.getTimezoneOffset();
			var dataToPost = {action: 'doSignup', first_name: $scope.user.fname, last_name: $scope.user.lname, email: $scope.user.email, 
			password: hash, timezone_name: timezoneName, timezone_offset: timezoneOffset};
			var httpRequest =  Authentication.sendHTTPRequest("/signup", dataToPost)
				.then(function(dataObj) {
					if(!dataObj.status) {
						$ngBootbox.alert('Password matched');
					} else {
						noty({text: "Password don't match ", type: "error", theme: "relax", layout: "topCenter", timeout: 3000});
					}
			}, function(x) {
				$scope.authError = 'Unable to connect the Server';
				$scope.signup_progress = false;
				noty({text: $scope.authError, type: "error", theme: "relax", layout: "topCenter", timeout: 3000});
			});
		} else {
			alert();
			$scope.user.progress = false;
        	noty({text:"Password doesn't match", type: 'error', theme:'relax', layout:'topCenter', timeout:3000});
		}
	};

}]);