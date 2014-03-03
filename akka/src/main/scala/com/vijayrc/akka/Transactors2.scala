package com.vijayrc.akka

import akka.transactor.Transactor
import scala.concurrent.stm.Ref
import akka.actor._
import akka.pattern.ask
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.util.Timeout

case object Msg1
case object Msg2
case object Say

/** */
class Peer1(peer:ActorRef) extends Transactor with ActorLogging{
  val count = Ref(0)
  def atomically = implicit txn ⇒ {
    case Msg1 => {log.info("atomically"); count transform (_ + 1)}
    case Say => sender ! count.single.get
  }
  override def coordinate = {
    case Msg1 ⇒ {log.info("coordinate");include(peer)}
  }
}
/** */
class Peer2 extends Transactor with ActorLogging{
  val count = Ref(1)
  def atomically = implicit txn ⇒ {
    case Msg1 => {log.info("atomically"); count transform (_ + 1)}
    case Say => sender ! count.single.get
  }
}
/** */
object Transactors2 {
   def work(){
     val system  = ActorSystem.create("system")
     try {
       val c2 = system.actorOf(Props[Peer2], "c2")
       val c1 = system.actorOf(Props.create(classOf[Peer1], c2), "c1")
       c1 ! Msg1

       implicit val t = Timeout(5 seconds)
       val r1 = Await.result(c1 ? Say,5 seconds)
       val r2 = Await.result(c2 ? Say,5 seconds)
       println(r1+"|"+r2)
     }
     finally {
       system.shutdown()
     }
   }

}
object Transactors2Test extends App{
  Transactors2.work()
}
