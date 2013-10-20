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
    this.boot = function(){
       stylizeTable();
       setupEventSelect();
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