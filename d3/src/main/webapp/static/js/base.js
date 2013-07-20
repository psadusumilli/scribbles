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
        var y = d3.scale.ordinal().domain(data).rangeBands([0, 120]);
        var chart = d3.select("#sample-3");

        //bars
        chart.selectAll("rect").data(data)
        .enter().append("rect")
        .attr("y", function(d, i) { return i * 20; })
        .attr("width", x).attr("height", 20).attr("fill","steelblue")
        .attr("stroke","white");

        //bar text labels
        chart.selectAll("text")
        .data(data)
        .enter().append("text")
        .attr("x", x)
        .attr("y", function(d) { return y(d) + y.rangeBand() / 2; })
        .attr("dx", -3) // padding-right
        .attr("dy", ".35em") // vertical-align: middle
        .attr("text-anchor", "end") // text-align: right
        .text(String);

        //vertical tick lines
        chart.selectAll("line")
        .data(x.ticks(10))
        .enter().append("line")
        .attr("x1", x).attr("x2", x).attr("y1", 0).attr("y2", 120)
        .style("stroke", "#ccc");

        //vertical tick line labels
        chart.selectAll(".rule")
        .data(x.ticks(10))
        .enter().append("text")
        .attr("class", "rule")
        .attr("x", x)
        .attr("y", 0)
        .attr("dy", -3)
        .attr("text-anchor", "middle")
        .text(String);

        //border left line
        chart.append("line")
        .attr("y1", 0)
        .attr("y2", 120)
        .style("stroke", "#000");
    };


    var sample4 = function(){
      d3.select("#sample-4-1").style("color","grey").transition().delay().style("color","yellowgreen");
      d3.select("#sample-4-2").transition()
          .delay(750)
          .each("start", function() { d3.select(this).style("color", "green"); })
          .style("color", "red");

    };

    this.boot = function(){
      sample1();
      sample2();
      sample3();
      sample4();
    };

}

$(document).ready(function(){new D3Test().boot()});

