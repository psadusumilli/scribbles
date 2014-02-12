package com.vijayrc.akka

import akka.actor._
import akka.actor.Terminated
import akka.event.Logging

object DeathWatch {

  class WatchActor extends Actor {
    val log = Logging(context.system, this)
    var deadLetters:ActorRef = null
    var child:ActorRef = null

    override def preStart() {
      child = context.actorOf(Props.empty, "child")
      context.watch(child)
      deadLetters = context.system.deadLetters
    }
    def receive = {
      case "kill" => {log.info("killing child");context.stop(child)}
      case Terminated(child) => deadLetters ! "finished"
    }
  }
  def work(){
    val system =  ActorSystem.create("system1")
    try {
      val watcher: ActorRef = system.actorOf(Props[WatchActor], "watcher")
      watcher ! "kill"
      Thread.sleep(1000)
    }
    finally system.shutdown()

  }

}
object DeathWatchTest extends App{
  DeathWatch.work()
}
