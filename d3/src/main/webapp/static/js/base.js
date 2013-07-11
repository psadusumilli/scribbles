D3Test = function(){

    var sample1 = function(){
       d3.select("#sample-1").selectAll("p")
            .data([8, 12, 15, 20, 33, 42])
            .enter().append("p")
            .text(function(d) { return "I am a new dude " + d + "!";})
            .style("font-size", function(d) { return d + "px";})
            .style("padding-top",function(d) { return 0.5*d + "px";});
    };

    this.boot = function(){
      sample1();
    };

}

$(document).ready(function(){new D3Test().boot()});

