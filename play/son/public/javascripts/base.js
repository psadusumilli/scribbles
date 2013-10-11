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
        var colors = [#1874CD,#CD5C5C,#218868]
        
    };

    var showEvent = function(response){
       eventBox.dialog({"height":600, "width":900, modal:true});
       eventBox.html(response)
    };

    this.boot = function(){
       setupEventSelect();
    };
};

$(document).ready(function(){new EventsPage().boot()});