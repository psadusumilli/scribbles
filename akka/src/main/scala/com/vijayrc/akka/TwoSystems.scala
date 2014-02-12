package com.vijayrc.akka

import akka.actor.{Props, ActorSystem, Actor}
import akka.event.Logging

class A1 extends Actor{
  val log = Logging(context.system, this)

  def receive = {
    case x:String => log.info("actor1|"+x)
  }
  override def postStop() {
    log.info("actor1|stopping")
    super.postStop()
  }
}
class A2 extends Actor{
  val log = Logging(context.system, this)

  def receive = {
    case x:String => log.info("actor2|"+x)
  }
  override def postStop() {
    log.info("actor2|stopping")
    super.postStop()
  }
}

class TwoSystems {

  def work(){
    val system1 = ActorSystem.create("system1")
    val system2 = ActorSystem.create("system2")
    try {
      val actor1 = system1.actorOf(Props[A1],"actor1")
      val actor2 = system2.actorOf(Props[A2],"actor2")
      actor1 ! "msg1"
      actor2 ! "msg2"
    }
    finally {
      system1.shutdown()
      system2.shutdown()
    }
  }

}
object Test2 extends App{
  new TwoSystems().work()
}