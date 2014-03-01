package com.vijayrc.concurrency

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Futures {
  def current(msg:String) {println(msg+"|"+Thread.currentThread())}
  def sleep(x:Long){Thread.sleep(x)}

  def callback(){
    val fc1:Future[String] = future {current("fc1-exec");"fc1-returns"}//executed in another thread, no blocking here
    val fc2:Future[String] = future {current("fc2-exec");"fc2-returns"}

    fc1 onSuccess{case x => current("fc1-success|"+x)}
    fc1 onFailure{case e:Exception => current("fc1-fail"+e.getMessage)}
    Await.result(fc1,10 seconds)

    fc2.onComplete{
      case Success(x) => current("fc2-success|"+x)
      case Failure(e) => current("fc2-fail"+e.getMessage)
    }
    Await.result(fc2,10 seconds)
  }

  def chaining() {
    val f1 = Future {current("f1-exec"); "hello world" }
    val f2 = f1 map {current("f2-exec"); x ⇒ x.length }

    f1.onSuccess{case x => current("f1-success|"+x)}
    f2 foreach println //foreach is similar ton onSuccess
    Await.result(f2,3 seconds)//to block until future completes
  }

  def chainFor(){
    //compare values returned from 2 futures, return the maximum
    //using for comprehension
    def random: Double = {Math.random() * 1000}
    val f1 = future{sleep(2000); val x = random; println("f1="+x); x}
    val f2= future{sleep(1000); val x = random; println("f2="+x); x}
    try {
      val f3 = for {
        i <- f1
        j <- f2
        if i > j
      } yield i + 10
      f3 foreach(x => println("f3="+x))
      Await.result(f3,10 seconds)
    }
    catch {
      case e:NoSuchElementException => print("f3 condition failed")
    }
    //using flatmap to achieve the same result
    //fails silently if filter condition fails
    f1.flatMap{i => f2.withFilter(j => j < i).map(j => j+10)}.foreach(x => println("f3="+x))
  }


}

object FuturesTest extends App{
  import Futures._
  //chaining()
  //callback()
  chainFor()
}