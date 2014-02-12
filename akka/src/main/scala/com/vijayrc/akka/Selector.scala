package com.vijayrc.akka

import akka.actor._
import akka.actor.ActorIdentity
import scala.Some
import akka.event.Logging

/*actor2 follows actor1 using path for actor selection*/
object Selector {

  class Actor2 extends Actor{
    val log =  Logging(context.system, this)
    def receive = {
      case x:String =>  log.info("received" +x)
    }
  }

  class Actor1 extends Actor {
    val id = 1
    val log =  Logging(context.system, this)
    override def preStart() { context.actorSelection("/user/actor2") ! Identify(id) }
    def receive = {
      case ActorIdentity(identifyId, Some(ref)) => {
        context.watch(ref)
        context.become(onActor2Stop(ref))
        log.info("actor2 is found, actor1 will watch actor2 for termination.")
        log.info("using 'become' to add actor1 behaviour when actor2 terminates")
      }
      case ActorIdentity(identifyId, None) => {
        log.info("actor2 is not found, actor1 stopping")
        context.stop(self)
      }
    }
    def onActor2Stop(actor2: ActorRef): Actor.Receive = {
      case Terminated(`actor2`) => {
        log.info("actor2 stopped, so actor1 stopping")
        context.stop(self)
      }
    }
  }

  def work(){
    val system = ActorSystem.create("system1")
    try {
      val actor2 = system.actorOf(Props[Actor2], "actor2")
      val actor1 = system.actorOf(Props[Actor1], "actor1")
      Thread.sleep(1000)
      system.stop(actor2)
      Thread.sleep(2000)
    }
    finally system.shutdown()
  }
}

object SelectorTest extends App{
  Selector.work()
}
