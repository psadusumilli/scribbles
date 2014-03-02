package com.vijayrc.akka

import akka.agent.Agent
import akka.actor.ActorSystem
import scala.concurrent.{Await, Future, ExecutionContext}
import java.util.concurrent.Executors
import scala.concurrent.duration._

object Agents {
  def sleep(x:Long) = Thread.sleep(x)

  def updateWithValue(){
    val system: ActorSystem = ActorSystem.create("system")
    implicit val dispatcher = system.dispatcher
    try {
      val a1 = Agent(5)
      a1.send(10)
      sleep(2000)//give sufficient time for the asynch update to happen
      println("a1="+a1.get())//10

      val f1 = a1.alter(20)
      f1.onSuccess{case _ => println("f1-done")}
      Await.ready(f1,3 seconds)
      println("a1="+a1.get())//20

    }
    finally system.shutdown()
  }

  def updateWithFunction(){
    implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(5))
    try {
      val a1 = Agent(5)
      a1.send(x => x * 4) //sending an update function
      sleep(2000)
      println(a1())//20

      val f1 = a1.alter(x => x * 3)
      f1.onSuccess{case _ => println("f1-done")}
      Await.ready(f1,3 seconds)
      println("a1="+a1.get())//60

    }
    finally ec.shutdown()
  }



}
object AgentsTest extends App{
  import Agents._
  //updateWithValue()
  updateWithFunction()
}