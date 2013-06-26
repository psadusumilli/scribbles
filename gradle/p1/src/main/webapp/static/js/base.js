JukeBox = function(){

    var playMusic = function(){
        var option = $("select option:selected").text();
        alert(option);
    };

    this.boot = function(){
        $("#playlist").change(playMusic);
    };

};


$(document).ready(function() {
	new JukeBox().boot();
});