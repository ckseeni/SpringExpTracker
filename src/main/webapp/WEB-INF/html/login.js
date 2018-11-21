var app = angular.module('loginApp', []);
app.controller('loginController', function($scope, $http) {
	
	$scope.onLogin = function() {
	
	};
	
	$scope.onRegister = function() {
		if($scope.loginForm.$valid) {
			var userObj = {
					"username" : $scope.username,
					"password" : $scope.password
			}
			userObj = JSON.stringify(userObj);
			$http.post('http://localhost:8080/ExpTracker/addUser/',userObj)
			.then(function(response) {
				alert("Registration successfull!!"+response);
			}, function() {
				alert("Registration Failed!!");
			});
		}
		else {
			alert("Fill all fields!!");
		}
	};
});