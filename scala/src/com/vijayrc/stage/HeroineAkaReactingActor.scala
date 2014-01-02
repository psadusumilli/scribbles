package com.vijayrc.stage


class HeroineAkaReactingActor extends BaseActor("HEROINE=>"){
  def act(){
    println(Thread.currentThread().getName+"|"+name+ "started")
    //since there is a loop, this actor will react to all messages sent
    loop{
      react{
        case(dialogue:String,actor:BaseActor) => println(Thread.currentThread().getName+"|"+name+actor.getName+"|"+dialogue)
        case dialogue => println (name+dialogue)
        }
     }
  }

}
