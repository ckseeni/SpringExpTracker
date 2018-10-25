var app = angular.module('expListApp', []);
app.controller('expListController', function($scope, $http, $window) {
	
	$scope.loadExpList = function() {
		$scope.expList = [{name:"loading"}];
		$http.get('http://localhost:8080/ExpTracker/retriveExp/')
		.then(function(response) {
			var expArr = JSON.parse(response.data.expList);
			return expArr;
		}, function() {
			return "";
		}).then(function(expArr) {
			$scope.expList = expArr;
		});
	};
	
	$scope.clearExpList = function() {
		if($window.confirm("Data will be deleted Permanently!!\nDo you want to continue?")) {
			$http.delete('http://localhost:8080/ExpTracker/delExp/')
			.then(function() {
				alert("Expenses list cleared!!");
				$scope.loadExpList();
			}, function() {
				alert("error");
			});
		}
	};

});