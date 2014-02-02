var module1 = angular.module('m1',['ngRoute']);

/*girls ----------------------------------------------------------------------*/

/*constant injection*/
module1.constant("title","SouthPark Elementary")

/*value injection*/
var GirlsRepo = function(title){
    this.title = title;
    this.all = function(){return [{name:'bebe',id:1},{name:'wendy',id:2},{name:'shelly',id:3},{name:'red',id:4}]};
}
module1.value("girlsRepo", new GirlsRepo());//no dependency on anything else

/*service injection*/
var GirlsService = function(girlsRepo){
  this.title = girlsRepo.title
  this.all = girlsRepo.all()
}
module1.service("girlsService", GirlsService)//now new keyword required

/*controllers*/
var controllers = {};
controllers.controller2 = function($scope,girlsService){
  $scope.girls = girlsService.all;
  $scope.title = girlsService.title;
};

/*boys ----------------------------------------------------------------------*/


controllers.controller1 = function($scope){
  $scope.boys=[{name:'stan',id:1},{name:'cartman',id:2}, {name:'kenny',id:3}, {name:'kyle',id:4}];
  $scope.add = function(){
     $scope.boys.push({name:$scope.newboy.name,id:$scope.newboy.id});
  };
};


controllers.controller3 = function($scope){
  $scope.s0="D";
};

module1.controller(controllers);

/*routers*/
module1.config(function($routeProvider){
    $routeProvider
                        .when('/all',{controller:'controller1',templateUrl:'all.html'})
        .when('/boys',{controller:'controller1',templateUrl:'boys.html'})
        .when('/girls',{controller:'controller2',templateUrl:'girls.html'})
        .when('/scope',{controller:'controller3',templateUrl:'scope.html'})
        .otherwise({redirectTo:'/all'});
});




