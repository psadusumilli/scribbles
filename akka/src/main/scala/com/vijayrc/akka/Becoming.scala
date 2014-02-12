package com.vijayrc.akka

import akka.actor.ActorSystem
import akka.actor.ActorDSL._
import akka.event.{LoggingAdapter, Logging}

/*
* Act is trait, we are providing a implementation below for its methods like become, when*
* implicit 'system' supplied to 'actor' method below
*/
object Becoming {
  implicit val system = ActorSystem("system1")

  val actor1 = actor(new Act{
    private val log: LoggingAdapter = Logging(context.system, this)

    become{
      case "info" => sender ! "I am good"
      case "switch" => becomeStacked{ //stack this behaviour over existing
        case "info" => sender ! "I am bad"
        case "switch" => unbecome() //unstack
      }
      case "clear" => unbecome()//clear all behaviour
      case "die" => throw new Exception("super bad")
    }
    whenStarting { log.info("started") }
    whenStopping { log.info("stopped") }
    whenFailing { case m @ (cause, msg) ⇒ log.error("failed|"+m.toString) }
    whenRestarted { cause ⇒ log.error("restarting") }
  })

  val actor2 = actor(new Act{
    private val log: LoggingAdapter = Logging(context.system, this)

    whenStarting({
      actor1 ! "info"
      actor1 ! "switch"
      actor1 ! "info"
    })
    become{
      case x => log.info("received|"+x)
    }
  })

  def work(){
    try {
      actor1 ! "die"
      Thread.sleep(1000)
    }
    finally system.shutdown()
  }
}

object BecomingTest extends App{
  Becoming.work()
}