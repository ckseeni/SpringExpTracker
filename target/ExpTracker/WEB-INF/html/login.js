var app = angular.module('loginApp', []);
app.controller('loginController', function($scope, $http) {
	
	$scope.onLogin = function() {
		if($scope.loginForm.$valid) {
			var userObj = {
					"username" : $scope.username,
					"password" : $scope.password
			}
			userObj = JSON.stringify(userObj);
			$http.post('http://localhost:8080/ExpTracker/authenticateUser/',userObj)
			.then(function() {
				window.location = "expMain.html";
			}, function() {
				alert("Login Failed!!");
			});
		}
		else {
			alert("Fill all fields!!");
		}	
	};
	
	$scope.onRegister = function() {
		if($scope.loginForm.$valid) {
			var userObj = {
					"username" : $scope.username,
					"password" : $scope.password
			}
			userObj = JSON.stringify(userObj);
			$http.post('http://localhost:8080/ExpTracker/addUser/',userObj)
			.then(function() {
				alert("Registration successfull!!");
			}, function() {
				alert("Registration Failed!!");
			});
		}
		else {
			alert("Fill all fields!!");
		}
	};
});