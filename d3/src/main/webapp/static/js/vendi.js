Diagram1 = function(){
  var svg = d3.select("#diagram-1");

  this.boot = function(){
      var postSeed = new PostSeed().boot();
      drawNodes(postSeed.allPosts());
  };

  var drawNodes = function(data){
     svg.selectAll("circle").data(data).enter().append("circle")
     .attr("r",function(d){return d.tagKeys.length})
     .attr("cx",10)
     .attr("cy",function(d,i){return i*20;});
  };


};




//--------------- SEED  ---------------------------
Post = function(name){
   this.name = name;
   this.tags = {};
   this.tagKeys = [];

   this.addTag = function(tag){
     this.name = name;
     this.tagKeys.push(tag.name);
     this.tags[tag.name] = tag;
     tag.addPost(this);
   };
};
//-------------------------
Tag = function(name){
   this.name = name;
   this.posts = {};
   this.postKeys = [];

   this.addPost = function(post){
     this.postKeys.push(post.name);
     this.posts[post.name] = post;
   };

   this.hasPost = function(post){
     return posts[post.name] != null;
   };
};
//-------------------------
TagSeed = function(){
   var tags = new Array(8);

   this.random = function(){
     var randomSize = Math.floor((Math.random()*4)+3); //[3-7]tags/post
     var randomTags = new Array(randomSize);
     for(var i=0; i<randomSize; i++){
          var index = Math.floor(Math.random()*tags.length);
          randomTags[i] = tags[index];
     }
     return randomTags;
   };

   this.boot = function(){
     for(var i=0; i<tags.length; i++){
        tags[i] = new Tag("tag_"+i);
     }
     return this;
   };

   this.allTags = function(){return tags};
};
//-------------------------
PostSeed = function(){
   var posts = new Array(50);
   var tagSeed = new TagSeed().boot();

   this.boot = function(){
     for(var i=0; i < posts.length; i++) {
        posts[i] = new Post("post_"+i);
        var tags = tagSeed.random();
        for(var j=0; j<tags.length; j++){
            posts[i].addTag(tags[j]);
        }
     }
     return this;
   };

   this.allPosts = function(){return posts};
   this.allTags = function(){return tagSeed.allTags()};
};
//-----------------------------------------------------
$(document).ready(function(){new Diagram1().boot()});