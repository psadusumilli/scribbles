package com.vijayrc.actors

import scala.actors.Actor

/**
* When the receive primitive is used, the actor is internally backed by a dedicated thread.
* This obviously limits scalability and requires the thread to suspend and block when waiting for new messages.
*/
class ThreadActor extends Actor{
  val threads = Set
  def act() {
    var count = 0
    loop{
      receive{
        case msg:String => count+=1;println("thread="+threads+=Thread.currentThread()+"count="+count+"|received "+msg)
      }
    }
  }
}
class ThreadActor2 extends Actor{
  def act() {
    while(true){
      receive{
        case msg:String => println("thread="+Thread.currentThread()+"|received "+msg)
      }
    }
  }
}

/**
 * The react primitive allows and event-driven execution strategy,
 * which does not directly couple actors to threads.
 * Instead, a thread pool can be used for a number of actors.
 * This approach uses a continuation closure to encapsulate the actor and its state
 */
class EventActor extends Actor{
  def act() {
    var count = 0
    loop{
      react{
        case msg => count+=1;println("thread="+Thread.currentThread()+"count="+count+"|received "+msg)
      }
    }
  }
}

object Stage{
  def main(args: Array[String]) {
    def noLoopThreadActor {
      val actor = new ThreadActor2
      actors(actor)
      Thread.sleep(30000)
    }
    def actors(actor:Actor) {
      actor.start()
      for (i <- 1 until 20000)
        actor ! "msg-" + i
    }
    //noLoopThreadActor
    //actors(new EventActor)
    actors(new ThreadActor)


    println("curtain down!")
  }
}
