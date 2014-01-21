var module1 = angular.module('m1',[]);
var controllers = {};

controllers.controller1 = function($scope){
  $scope.boys=[
       {name:'stan',id:1},
       {name:'cartman',id:2},
       {name:'kenny',id:3},
       {name:'kyle',id:4}]
};

module1.controller(controllers);
