Vendi = function(){
  this.boot = function(){
      var posts = new PostSeed().boot();
  };




};






//--------------- SEED  ---------------------------
Post = function(name, tags){
   this.name = name;
   this.tags = tags;
};
Tag = function(name){
   this.name = name;
   this.numberOfPosts = 0;

   this.increment(){
        ++numberOfPosts ;
   };
};
//--------------------------------------------------
PostSeed = function(){
   var posts = new Array(200);
   var tagSeed = new TagSeed().boot();

   this.boot = function(){
     for(var i=0;i<posts.length;i++) {
        var tags = tagSeed.random();
        posts[i] = new Post("post_"+i, tags);
     }
     return posts;
   };
};
//--------------------------------------------------
TagSeed = function(){
   var tags = new Array(10);

   this.random = function(){
     var size = Math.floor((Math.random()*4)+3); //[3-7]tags/post
     var randomTags = new Array(size);
     for(var i=0;i<size;i++){
          var index = Math.floor(Math.random()*10);
          randomTags[i] = tags[index];
     }
     return randomTags;
   };

   this.boot = function(){
     for(var i=0;i<tags.length;i++){
        tags[i] = new Tag("tag_"+i);
     }
     return this;
   };
};

//-----------------------------------------------------
$(document).ready(function(){new Vendi().boot()});