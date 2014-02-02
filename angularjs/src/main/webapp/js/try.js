var module1 = angular.module('m1',['ngRoute']);

/*girls ----------------------------------------------------------------------*/

/*constant injection*/
module1.constant("title","SouthPark Elementary")

/*value injection - no dependency on anything else*/
var GirlsRepo = function(title){
    this.title = title;
    this.all = function(){return [{name:'bebe',id:1},{name:'wendy',id:2},{name:'shelly',id:3},{name:'red',id:4}]};
}
module1.value("girlsRepo", new GirlsRepo());//

/*service injection - rarely used*/
var GirlsService = function(girlsRepo){
  this.title = girlsRepo.title
  this.all = girlsRepo.all()
}
module1.service("girlsService", GirlsService)//now new keyword required, singletons

/*controllers*/
var controllers = {};
controllers.girlsController = function($scope,girlsService){
  $scope.girls = girlsService.all;
  $scope.title = girlsService.title;
};

/*boys ----------------------------------------------------------------------*/

/*factory injection*/
module1.factory("boysRepo", function(){
   var boys=[{name:'stan',id:1},{name:'cartman',id:2}, {name:'kenny',id:3}, {name:'kyle',id:4}]
   return{
     all:boys,
     add:function(boy){boys.push(boy)}
   }
})
/*provider injection*/
module1.provider("boysService", function(){
    var config = {max:6}
    return{
        setMax:function(max){config.max = max?max:config.max},
        $get:function(boysRepo){
            return {//this obj is boysService during run phase
                all:boysRepo.all,
                add:function(boy){
                    if(this.all.length >= config.max) {return "maxed-out"}
                    else boysRepo.add(boy)
                }
            }
        }
    }
});
/*controllers*/
controllers.boysController = function($scope,boysService){
  $scope.boys = boysService.all
  $scope.add = function(){
     boysService.add({id:$scope.newboy.id, name:$scope.newboy.name});
  };
};
/*misc ----------------------------------------------------------------------*/
controllers.scopeController = function($scope){
  $scope.s0="D";
};

module1.controller(controllers);

/*routers*/
module1.config(function($routeProvider){
    $routeProvider
        .when('/all',{controller:'scopeController',templateUrl:'all.html'})
        .when('/boys',{controller:'boysController',templateUrl:'boys.html'})
        .when('/girls',{controller:'girlsController',templateUrl:'girls.html'})
        .when('/scope',{controller:'scopeController',templateUrl:'scope.html'})
        .otherwise({redirectTo:'/all'});
});




