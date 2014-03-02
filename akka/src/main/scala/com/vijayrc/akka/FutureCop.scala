package com.vijayrc.akka

import org.scalatest.FunSuite
import scala.concurrent.{ExecutionContext, Future}
import akka.actor.{Props, ActorSystem, Actor}
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout


class FutureActor extends Actor{
  import context.dispatcher
  def receive = {
    case "internal" => {
      val f1 = Future{"f1-returns"}
      f1 foreach println
    }
    case "ask" => sender ! "f2-returns"
  }
}
class FutureCop extends FunSuite{
  val system = ActorSystem.create("system")
  val actor = system.actorOf(Props[FutureActor],"f-actor")
  implicit val timeout = Timeout(5 seconds)

  test("futures should work with actor's dispatcher"){
      actor ! "internal"
  }

  test("futures returned as ask using global execution context"){
    import ExecutionContext.Implicits.global
    val f2 = actor ? "ask"
    f2.onSuccess{case x => println(x)}
  }


}
