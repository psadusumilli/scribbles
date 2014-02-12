package com.vijayrc.akka
import akka.actor.{ActorSystem, Actor, Props}
import akka.Main
import scala.io.Source
import java.nio.file.Files
import java.io.{File, FileWriter}

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
    settings()
    val greeter = context.actorOf(Props[Actor2], "actor2")
    greeter ! Message.Greet
  }

  def settings() {
    val f = new File("config.json")
    if(f.exists()) return
    val fw = new FileWriter(f)
    try fw.write(context.system.settings.toString()) finally {fw.flush(); fw.close()}
  }

  def receive = {
    case Message.Done ⇒ println("actor1: thanks actor2");context.stop(self)
  }
}

object Test extends App{
  Main.main(Array("com.vijayrc.akka.Actor1"))
}
