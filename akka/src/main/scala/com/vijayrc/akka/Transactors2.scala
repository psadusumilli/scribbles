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
class Peer1(peer2a:ActorRef,peer2b:ActorRef) extends Transactor with ActorLogging{
  val count = Ref(0)
  def atomically = implicit txn ⇒ {
    case Msg1 => {log.info("atomically"); count transform (_ + 1)}
    case Say => sender ! count.single.get
  }
  override def coordinate = {
    case Msg1 ⇒ {log.info("coordinate-msg1");include(peer2a,peer2b)}
    case Msg2 ⇒ {log.info("coordinate-msg2");sendTo(peer2a -> "msg2",peer2b -> "msg2")}
  }
}
/** */
class Peer2 extends Transactor with ActorLogging{
  val count = Ref(1)
  def atomically = implicit txn ⇒ {
    case Msg1 => {log.info("atomically-msg1"); count transform (_ + 1)}
    case x:String => log.info("atomically-"+x)
    case Say => sender ! count.single.get
  }
}
/** */
object Transactors2 extends App{
   def work(){
     val system  = ActorSystem.create("system")
     try {
       val p2b = system.actorOf(Props[Peer2], "p2b")
       val p2a = system.actorOf(Props[Peer2], "p2a")
       val p1 = system.actorOf(Props.create(classOf[Peer1], p2a, p2b), "p1")
       p1 ! Msg1

       implicit val t = Timeout(5 seconds)
       val r1 = Await.result(p1 ? Say,5 seconds)
       val r2 = Await.result(p2a ? Say,5 seconds)
       println("r1="+r1+"|r2="+r2)

       p1 ! Msg2
     }
     finally {
       system.shutdown()
     }
   }

}
object Transactors2Test extends App{
  Transactors2.work()
}
