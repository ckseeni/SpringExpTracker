var app = angular.module('expApp', []);
app.controller('expController', function($scope, $http, $location) {
	
	$scope.addExpense = function() {
		if($scope.expForm.$valid) { 
			var date = new Date();
			var dtString = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " +  date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
			var obj = {
				"name" : $scope.name,
				"amount" : $scope.amount,
				"dateAndTime" : dtString
			};
			var expData = JSON.stringify(obj);
			$http.post('http://localhost:8080/ExpTracker/addExp/',expData)
			.then(function() {
				alert(obj.name+"\n"+obj.amount+"\n stored on "+obj.dateAndTime);
			}, function(response) {
				alert("Error while storing data in server!!!");
			});
		}
		else {
			alert("fill all fields");
		}
	};	
	
	$scope.retrieveExpense = function() {
		window.location = "expList.html";
	};
		
	$scope.filterAmount = function($event) {
		$scope.amountError = "";
		if($event.keyCode <48 || $event.keyCode >57) {
			$scope.amountError = "Only numbers are allowed";	
		}
	}; 	
	
	$scope.logOut = function() {
		$http.get('http://localhost:8080/ExpTracker/logout')
		.then(function() {
			window.location = "index.html";
		}, function() {
			alert("Error in logging out!!");
		});
	};
	
	$scope.importCSV = function() {
		var fd = new FormData();
		alert($scope.files);
	};
	
});