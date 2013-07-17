D3Test = function(){

    var sample1 = function(){
       d3.select("#sample-1").selectAll("p")
            .data([8, 12, 15, 20, 33, 42])
            .enter().append("p")
            .text(function(d) { return "I am a new dude " + d + "!";})
            .style("font-size", function(d) { return d + "px";})
            .style("padding-top",function(d) { return 0.5*d + "px";});
    };

    var sample2 = function(){
        var circles = d3.select("#sample-2").selectAll("circle");
        circles.data([8, 12, 15, 20])
        .enter().append("circle")
        .attr("cx",function(d,i){ return Math.random() * 100;})
        .attr("cy",function(d,i){ return 100 * (i+1);})
        .attr("r",function(d){ return d;});
    };

    this.boot = function(){
      sample1();
      sample2();
    };

}

$(document).ready(function(){new D3Test().boot()});

