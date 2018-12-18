var app = angular.module('expListApp', []);
app.controller('expListController', function($scope, $http, $window) {
	
	$scope.totalExp = 0;
	
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
			for (item in expArr) {
				$scope.totalExp = $scope.totalExp + Number(expArr[item].amount);
			}
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

	$scope.onExpListClick = function(item) {
		alert(item.name + ":" + item.amount + "\nstored on " + item.dateAndTime);
	};
	
	function generateCSV() {
		var result = '',  
			ctr,
			keys,
			colDelimiter = ',', 
			lineDelimiter = '\n',
			data = $scope.expList;
		
		keys = Object.keys(data[0]);
		
		result = "Name,Amount";
		result += lineDelimiter;

		data.forEach(function(item) {
			ctr = 0;
			keys.forEach(function(key) {
				if (key == "name" || key == "amount") {
					if (ctr > 0) result += colDelimiter;
					result += item[key];
					ctr++;
				}
			});
			result += lineDelimiter;
		});
		
		result = result + "Total Expenses" + colDelimiter + $scope.totalExp + lineDelimiter; 
		return result;
	}
	
	function downloadCSV() {  
        var csv = generateCSV();
        var filename = "ExpenseList.csv";
        csv = 'data:text/csv;charset=utf-8,' + csv;
        link = document.createElement('a');
        link.setAttribute('href', csv);
        link.setAttribute('download', filename);
        link.click();
    }
	
	$scope.exportCSV = function() {
		//downloadCSV();
		var csv = generateCSV();
		$http.post('http://localhost:8080/ExpTracker/emailExp',csv)
		.then(function() {
			alert("email sent successfully!!");
		}, function() {
			alert("Error in sending email!!");
		});
	};
	
	$scope.logOut = function() {
		$http.get('http://localhost:8080/ExpTracker/logout')
		.then(function() {
			window.location = "index.html";
		}, function() {
			alert("Error in logging out!!");
		});
	};
	
});