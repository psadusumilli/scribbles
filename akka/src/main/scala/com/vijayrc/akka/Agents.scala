package com.vijayrc.akka

import akka.agent.Agent
import akka.actor.ActorSystem
import scala.concurrent.{Await, ExecutionContext}
import java.util.concurrent.Executors
import scala.concurrent.duration._
import scala.concurrent.stm._

object Agents {
  def sleep(x:Long) = Thread.sleep(x)
  def context = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(5))

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
    implicit val ec = context
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

  def transaction(){
    implicit val ec = context
    def transfer(from:Agent[Int], to:Agent[Int], amt:Int):Boolean = {
      atomic{
          txn =>
          if(from.get() < amt) false
          else{
            to.send(_+amt)
            from.send(_-amt)
            true
          }
      }
    }
    try {
      val from = Agent(100)
      val to = Agent(30)
      transfer(from, to, 50)
      sleep(2000)
      println(from.get + "|" + to.get)
    }
    finally ec.shutdown()
  }

  def monads(){
    implicit val ec = context
    val a1 = Agent(1)
    val a2 = Agent(2)
    val a3 = a1 map (_ + 4)

    for (value <- a1)
      println(value)

    val a4 = for {
      i <- a1
      j <- a2
      k <- a3
    } yield k
    sleep(2000)
    println("a4="+a4.get())
  }

}
object AgentsTest extends App{
  import Agents._
  //updateWithValue()
//  updateWithFunction()
  transaction()
 // monads()
}