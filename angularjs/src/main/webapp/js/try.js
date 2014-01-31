var module1 = angular.module('m1',['ngRoute']);
var controllers = {};

controllers.controller1 = function($scope){
  $scope.boys=[
       {name:'stan',id:1},
       {name:'cartman',id:2},
       {name:'kenny',id:3},
       {name:'kyle',id:4}];

  $scope.add = function(){
     $scope.boys.push({name:$scope.newboy.name,id:$scope.newboy.id});
  };
};
controllers.controller2 = function($scope){
  $scope.girls=[
       {name:'bebe',id:1},
       {name:'wendy',id:2},
       {name:'shelly',id:3},
       {name:'red',id:4}]
};
controllers.controller3 = function($scope){
  $scope.s0="D";
};

module1.controller(controllers);

module1.config(function($routeProvider){
    $routeProvider
        .when('/all',{controller:'controller1',templateUrl:'all.html'})
        .when('/boys',{controller:'controller1',templateUrl:'boys.html'})
        .when('/girls',{controller:'controller2',templateUrl:'girls.html'})
        .when('/scope',{controller:'controller3',templateUrl:'scope.html'})
        .otherwise({redirectTo:'/all'});
});