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
      val counter1 = system.actorOf(Props[Counter], name = "counter1")
      val counter2 = system.actorOf(Props[Counter], name = "counter2")

      implicit val t = Timeout(5 seconds)
      counter1 ! Coordinated(Increment(Some(counter2)))

      val c1 = Await.result(counter1 ? GetCount, 5 seconds)
      val c2 = Await.result(counter2 ? GetCount, 5 seconds)
      println("c1=" + c1 + "|c2=" + c2)
    }
    finally system.shutdown()
  }
}
object TransactorsTest extends App{
  import Transactors._
  work()
}