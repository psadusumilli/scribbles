EventsPage = function(){
    var eventBox = $("#event");

    var setupEventSelect = function(){
       $(".event-row").click(function(){
            $.ajax({
                url: "/event/"+$(this).attr("id"),
                success: showEvent
            });
       });
    };

    var stylizeEventTable = function(){
        $(".events-table tr").each(function(){
            var randomNum = Math.ceil(Math.random()*9);
            if(randomNum % 2 == 0)
                $(this).children(".event-date").addClass("red-date");
            if(randomNum % 3 == 0)
                $(this).children(".event-location").addClass("blue-location");
            if(randomNum % 5 == 0)
                $(this).children(".event-date").addClass("green-date");

        });
        $(".events-table tr").hover(function() {
            $(this).addClass('hover');
        }, function() {
            $(this).removeClass('hover');
        });
    };

    var showEvent = function(response){
       eventBox.dialog({"height":600, "width":900, modal:true});
       eventBox.html(response);
    };

    this.boot = function(){
       stylizeEventTable();
       setupEventSelect();

    };
};

$(document).ready(function(){new EventsPage().boot()});