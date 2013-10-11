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

    var showEvent = function(response){
       eventBox.dialog();
       eventBox.html(response)
    };

    this.boot = function(){
       setupEventSelect();
    };
};

$(document).ready(function(){new EventsPage().boot()});