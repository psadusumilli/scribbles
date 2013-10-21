EventsPage = function(){
    var eventBox = $("#event");
    var eventNewBox = $("#new-event");
    var setupEventSelect = function(){
       $(".event-row").click(function(){
            $.ajax({
                url: "/event/"+$(this).attr("id"),
                success: showEvent
            });
       });
    };
    var setupNewEvent = function(){
       $(".new-event").click(function(e){
            $.ajax({
                url: "/event/new",
                success: showNewEvent
            });
            e.preventDefault();
            return false;
       });
    };
    var stylizeTable = function(){
        $(".event-table tr").each(function(){
            var randomNum = Math.ceil(Math.random()*9);
            if(randomNum % 2 == 0)
                $(this).children(".event-date").addClass("column-color1");
            if(randomNum % 3 == 0)
                $(this).children(".event-location").addClass("column-color2");
            if(randomNum % 5 == 0)
                $(this).children(".event-date").addClass("column-color3");

        });
        $(".event-table tr").hover(function() {
            $(this).addClass('hover');
        }, function() {
            $(this).removeClass('hover');
        });
    };
    var showEvent = function(response){
       eventBox.dialog({"height":600, "width":900, modal:true});
       eventBox.html(response);
    };
    var showNewEvent = function(response){
       eventNewBox.dialog({"height":600, "width":900, modal:true});
       eventNewBox.html(response);
       $("#event-datetime").datepicker({dateFormat:"yy-mm-dd"});
    };
    this.boot = function(){
       stylizeTable();
       setupEventSelect();
       setupNewEvent();
    };
};

LocationsPage = function(){
    var locationBox = $("#new-location");
    var setupNewLocation = function(){
       $(".new-location").click(function(e){
            e.preventDefault();
       });
    };
    var stylizeTable = function(){
        $(".location-table tr").each(function(){
            var randomNum = Math.ceil(Math.random()*9);
            if(randomNum % 2 == 0)
                $(this).children(".location-city").addClass("column-color1");
            if(randomNum % 3 == 0)
                $(this).children(".location-state").addClass("column-color2");
            if(randomNum % 5 == 0)
                $(this).children(".location-country").addClass("column-color3");

        });
        $(".location-table tr").hover(function() {
            $(this).addClass('hover');
        }, function() {
            $(this).removeClass('hover');
        });
    };
    var showNewLocation = function(response){
       locationBox.dialog({"height":600, "width":900, modal:true});
       locationBox.html(response);
    };
    this.boot = function(){
       stylizeTable();
       setupNewLocation();
    };
};

PersonsPage = function(){
    var personBox = $("#new-person");
    var setupNewPerson = function(){
       $(".new-person").click(function(e){
            $.ajax({
                url: "/person/new",
                success: showDialog
            });
            e.preventDefault();
            return false;
       });
    };
    var showDialog = function(response){
        personBox.dialog({"height":600, "width":900, modal:true});
        personBox.html(response);
    }
    this.boot = function(){
       setupNewPerson();
    };
};



$(document).ready(function(){
    new EventsPage().boot();
    new LocationsPage().boot();
    new PersonsPage().boot();
    });