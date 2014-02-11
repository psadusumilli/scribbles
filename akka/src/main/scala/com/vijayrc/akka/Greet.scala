package com.vijayrc.akka
import akka.actor.Actor
import akka.actor.Props
import akka.Main

/*
* creates an actor2, tells it to print "hello" via Message case objects (immutable)
* actor2 replies to actor1 which on receiving actor1 stops
*/
object Message {
  case object Greet
  case object Done
}
class Actor2 extends Actor {
  def receive = {
    case Message.Greet ⇒ println("actor2: hey to actor1"); sender ! Message.Done
  }
}
class Actor1 extends Actor {
  override def preStart(): Unit = {
    val greeter = context.actorOf(Props[Actor2], "actor2")
    greeter ! Message.Greet
  }
  def receive = {
    case Message.Done ⇒ println("actor1: thanks actor2");context.stop(self)
  }
}

object Test extends App{
  Main.main(Array("com.vijayrc.akka.Actor1"))
}
