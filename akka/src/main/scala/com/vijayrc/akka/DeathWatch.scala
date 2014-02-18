package com.vijayrc.akka

import akka.actor._
import akka.actor.Terminated
import akka.event.Logging
import akka.pattern.gracefulStop
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

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
      case "kill" => log.info("killing child"); context.stop(child)
      case "poison" => log.info("poisoning child"); child ! PoisonPill
      case "graceful" => {
        log.info("graceful kill child")
        val stop: Future[Boolean] = gracefulStop(child, 5 seconds)
        stop.onComplete(x=> println(x))
      }
      case Terminated(child) => {log.info("child is dead")}
    }
  }
  def work(){
    val system =  ActorSystem.create("system1")
    try {
      val watcher = system.actorOf(Props[WatchActor], "watcher")
      //watcher ! "kill"
      //watcher ! "poison"
      watcher ! "graceful"
      Thread.sleep(1000)
    }
    finally system.shutdown()
  }
}
object DeathWatchTest extends App{
  DeathWatch.work()
}
