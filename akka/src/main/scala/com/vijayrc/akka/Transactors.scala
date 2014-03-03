package com.vijayrc.akka

import akka.actor._
import scala.concurrent.stm._
import akka.transactor.Coordinated
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask
import scala.Some
import akka.util.Timeout

case class Increment(peer: Option[ActorRef] = None)
case object GetCount

/** On receipt of a Increment message,
  * invokes its transaction peer to increment,
  * and also increments itself*/
class Counter extends Actor with ActorLogging{
  val count = Ref(0)

  def receive = {
    case coordinated @ Coordinated(Increment(peer)) => {
      peer foreach (_ ! coordinated(Increment()))
      coordinated atomic { implicit txn ⇒
        count transform (_ + 1)
        log.info(count.single.get+"")
      }

    }
    case GetCount ⇒ sender ! count.single.get
  }
}
/** try explicit transaction with 2 counters*/
object Transactors {
  def work(){
    val system = ActorSystem("app")
    try {
      val c1 = system.actorOf(Props[Counter], name = "c1")
      val c2 = system.actorOf(Props[Counter], name = "c2")

      implicit val t = Timeout(5 seconds)
      c1 ! Coordinated(Increment(Some(c2)))

      val r1 = Await.result(c1 ? GetCount, 5 seconds)
      val r2 = Await.result(c2 ? GetCount, 5 seconds)
      println("r1=" + r1 + "|r2=" + r2)
    }
    finally system.shutdown()
  }
}
object TransactorsTest extends App{
  import Transactors._
  work()
}