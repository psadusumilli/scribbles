package com.vijayrc.akka

import akka.actor.{ActorSelection, ActorSystem}
import akka.actor.ActorDSL._
import akka.event.{Logging, LoggingAdapter}

object Identify {
  implicit val system = ActorSystem("system1")

  val actor1 = actor("a1")(new Act{
    private val log: LoggingAdapter = Logging(context.system, this)
    become{
      case x:String => log.info(x)
    }
  })

  def work(){
    val selection  = ActorSelection(actor1,actor1.path.toString)
  }
}
