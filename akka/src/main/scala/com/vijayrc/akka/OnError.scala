package com.vijayrc.akka

import akka.actor.{ActorSystem, Props, Actor}

/*parent actor*/
class Parent extends Actor{
  println("constructor called")
  spawn()

  def receive={
    case "error" => throw new Exception("parent")
    case "c1-error" => context.child("c1").get ! "error"
    case "c2-error" => context.child("c2").get ! "error"
    case "c1-message" => context.child("c1").get ! "message"
    case x => print(x)
  }
  def spawn(){
    context.actorOf(Props.create(classOf[Child], "c1-title"), "c1")
    context.actorOf(Props.create(classOf[Child], "c2-title"), "c2")
  }
}
/*child actor*/
class Child(val title:String) extends Actor{
  def receive={
    case "error" => throw new Exception(title+" failed")
    case x => print(x)
  }
}
object OnError {
  def work(){
    val system = ActorSystem.create("system")
    try {
      val parent = system.actorOf(Props[Parent], "parent")
      parent ! "c1-error"
      parent ! "c1-message" //TODO not delivered
    }
    finally system.shutdown()
  }
}
object OnErrorTest extends App{
  OnError.work()
}
