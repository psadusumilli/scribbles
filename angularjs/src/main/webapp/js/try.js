var module1 = angular.module('m1',['ngRoute']);
var controllers = {};

module1.constant("title","SouthPark Elementary")

var GirlsRepo = function(){
    this.all = function(){return [{name:'bebe',id:1},{name:'wendy',id:2},{name:'shelly',id:3},{name:'red',id:4}]};
}
module1.value("girlsRepo", new GirlsRepo());//no dependency on anything else


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
controllers.controller2 = function($scope,girlsRepo,title){
  $scope.girls = girlsRepo.all();
  $scope.title = title;
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




