package com.vijayrc.akka

import akka.actor._
import akka.routing.RoundRobinRouter

class Routee extends Actor with ActorLogging{
  def receive = {
    case x:String => log.info(x)
  }
}
object Router {
  def work() {
    val system = ActorSystem.create("system1")
    try {
      var receivers = List[ActorRef]()
      for (a <- 1 until 5)
        receivers = system.actorOf(Props[Routee], "routee-" + a) :: receivers
      val router = system.actorOf(Props.empty.withRouter(RoundRobinRouter(routees = receivers)))
      for (i <- 1 until 100)
        router ! "msg-" + i
    }
    finally {
      Thread.sleep(4000)
      system.shutdown()
    }
  }
}
object RouterTest extends App{
  Router.work()
}
