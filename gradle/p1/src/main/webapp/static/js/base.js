JukeBox = function(){

    var playMusic = function(){
        var music = $("select option:selected").text().trim();
        $.ajax({
            url:"play/"+music,
            success:display
        });
    };

    var display = function(response){
       $("#display").html(response);
    };

    this.boot = function(){
        $("#playlist").change(playMusic);
    };

};

$(document).ready(function() {
	new JukeBox().boot();
});