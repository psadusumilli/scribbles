package com.vijayrc.akka

import akka.actor._
import akka.routing._

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

      val resizer = DefaultResizer(lowerBound = 2, upperBound = 15)
      val robin = system.actorOf(Props.empty.withRouter(RoundRobinRouter(routees = receivers)))
      val mailBox = system.actorOf(Props.empty.withRouter(SmallestMailboxRouter(routees = receivers)))
      val random = system.actorOf(Props.empty.withRouter(RandomRouter(routees = receivers)))
      val broadcast = system.actorOf(Props.empty.withRouter(BroadcastRouter(routees = receivers)))
      val grow = system.actorOf(Props[Routee].withRouter(RoundRobinRouter(resizer = Some(resizer))))
      val router = grow

      for (i <- 1 until 100)
        router ! "msg-" + i
      router ! Broadcast("all done?")
      router ! Broadcast(PoisonPill) //stops routees and then router


    }
    finally {
      Thread.sleep(2000)
      system.shutdown()
    }
  }
}
object RouterTest extends App{
  Router.work()
}
