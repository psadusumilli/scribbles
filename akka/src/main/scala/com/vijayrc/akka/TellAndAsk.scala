package com.vijayrc.akka

import akka.actor._
import org.joda.time.DateTime
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

object TellAndAsk {
  /*will reply to actor2*/
  class Actor1 extends Actor{
    def sleep() = Thread.sleep(3000)
    def receive = {
      case "short-tell" => println("actor1|short-tell"); sender ! "reply from short-tell"
      case "long-tell" => println("actor1|long-tell"); sleep(); sender ! "reply from long-tell"
      case "short-ask" => println("actor1|short-ask"); sender ! "reply from short-ask"
      case "long-ask" => println("actor1|tell with a reply"); sleep(); sender ! "reply from long-ask"
    }
  }
  /*tells/asks actor1 and bind futures for the same */
  class Actor2 extends Actor{
    var actor1:ActorSelection = null
    val id = 1
    implicit val timeout = Timeout(5 seconds)

    override def preStart(){
      actor1 = context.actorSelection("/usr/a1")
    }
    def receive = {
      case "send-short-tell" => actor1 ! "short-tell"
      case "send-long-tell" => actor1 ! "long-tell"
      case "send-short-ask" =>
        val future = actor1 ? "short-ask"
        future.onSuccess{case x:String => "future:"+x}
      case "send-long-ask" =>
        val future = actor1.ask("long-ask")
        future.onSuccess{case x:String => "future:"+x}
      case x:String => println("actor2|"+now+"|"+x)
    }

  }
  def work(){
    val system = ActorSystem.create("system1")
    try {
      val a2 = system.actorOf(Props[Actor2], "a2")
      a2 ! "send-short-tell"
      a2 ! "send-long-tell"
      a2 ! "send-short-ask"
      a2 ! "send-long-ask"
    }
    finally {
      Thread.sleep(6000)
      system.shutdown()
    }
  }
  def now = DateTime.now
}

object TellAndAskTest extends App{
  TellAndAsk.work()
}
