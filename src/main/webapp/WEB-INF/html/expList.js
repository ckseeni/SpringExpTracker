var app = angular.module('expListApp', []);
app.controller('expListController', function($scope, $http) {
	
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

});