package com.vijayrc.akka

import akka.actor._
import akka.actor.Terminated
import akka.event.Logging


/*WatchActor creates a child, kills, watches it die*/
object DeathWatch {
  class WatchActor extends Actor {
    val log = Logging(context.system, this)
    var child:ActorRef = null

    override def preStart() {
      child = context.actorOf(Props.empty, "child")
      context.watch(child)
    }
    def receive = {
      case "kill" => {log.info("killing child"); context.stop(child) }
      case Terminated(`child`) => {log.info("child is dead")}
    }
  }
  def work(){
    val system =  ActorSystem.create("system1")
    try {
      val watcher = system.actorOf(Props[WatchActor], "watcher")
      watcher ! "kill"
      Thread.sleep(1000)
    }
    finally system.shutdown()
  }
}
object DeathWatchTest extends App{
  DeathWatch.work()
}
