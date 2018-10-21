var app = angular.module('expApp', []);
app.controller('expController', function($scope, $http, $location) {
		
	$scope.addExpense = function() {
		if($scope.expForm.$valid) { 
			var obj = {
				"name" : $scope.name,
				"amount" : $scope.amount
			};
			var expData = JSON.stringify(obj);
			$http.post('http://localhost:8080/ExpTracker/addExp/',expData)
			.then(function() {
				alert(obj.name+"\n"+obj.amount+"\n stored");
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
});