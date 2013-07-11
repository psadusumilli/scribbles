D3Test = function(){

    var sample1 = function(){
         d3.selectAll("p")
             .data([4, 8, 15, 16, 23, 42])
             .style("font-size", function(d) { return d + "px"; });
    };

    this.boot = function(){
      sample1();
    };

}

$(document).ready(function(){new D3Test().boot()});

