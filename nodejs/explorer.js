require('fs');
fs.readdir('/home/vijayrc/docs', function (err, files) { // '/' denotes the root folder
  if (err) throw err;

   files.forEach( function (file) {
        fs.lstat('/'+file, function(err, stats) {
               if (!err && stats.isDirectory()) {
                        $('ul#foldertree').append('<li class="folder">'+file+'</li>');
                      }
               else{
                       $('ul#foldertree').append('<li class="file">'+file+'</li>');
                     }
             });
      });
}
