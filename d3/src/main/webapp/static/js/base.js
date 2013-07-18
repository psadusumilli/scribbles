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

    var sample3 = function(){
        var data = [8, 12, 15, 20, 33, 42];
        var x = d3.scale.linear().domain([0, d3.max(data)]).range([0, 420]);
        var rects = d3.select("#sample-3").selectAll("rect");

        rects.data(data)
        .enter().append("rect")
        .attr("y", function(d, i) { return i * 20; })
        .attr("width", x)
        .attr("height", 20)
        .attr("fill","steelblue")
        .attr("stroke","white");
    };

    this.boot = function(){
      sample1();
      sample2();
      sample3();
    };

}

$(document).ready(function(){new D3Test().boot()});

