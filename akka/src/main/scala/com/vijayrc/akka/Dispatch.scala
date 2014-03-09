package com.vijayrc.akka

import akka.actor.{Props, ActorLogging, Actor, ActorSystem}
import Util._

class DispatchActor extends Actor with ActorLogging{
  def receive = {
    case x:String => log.info(x)
  }
}
object Dispatch {
  def work(){
    val system = ActorSystem("system1", config("dispatcher-application.conf"))
    try {
      println("my-dispatcher exists?" + system.dispatchers.hasDispatcher("my-dispatcher"))
      val a1 = system.actorOf(Props[DispatchActor].withDispatcher("my-dispatcher"), "a1")
      a1 ! "hey dude"
    }
    finally system.shutdown()
  }
}
object DispatchTest extends App{
  Dispatch.work()
}