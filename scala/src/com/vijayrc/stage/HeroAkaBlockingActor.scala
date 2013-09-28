package com.vijayrc.stage


class HeroAkaBlockingActor extends BaseActor("HERO=>"){
  def act() {
    println(name+"started")
    //hero is important, evey actor calling him must wait until he finishes (receive)
    while (true){
        receive{
          case x:Int => println(name+"Got "+x+ "$, me rich")
          case dialogue => println(name+dialogue) //variable pattern matching
        }
    }
  }
}
