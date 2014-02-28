package com.vijayrc.concurrency

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Futures {
  def current(msg:String) {println(msg+"|"+Thread.currentThread())}
  def sleep(x:Long){Thread.sleep(x)}

  def chaining() {
    val f1 = Future {current("f1-exec"); "hello world" }
    val f2 = f1 map {current("f2-exec"); x ⇒ x.length }

    f1.onSuccess{case x => current("f1-success|"+x)}
    f2 foreach println
    Await.result(f2,3 seconds)//to block until future completes
  }

  def callback(){
    val fc1:Future[String] = future {current("fc1-exec");"fc1-returns"}
    val fc2:Future[String] = Future {current("fc2-exec");"fc2-returns"}

    fc1 onSuccess{case x => current("fc1-success|"+x)}
    fc1 onFailure{case e:Exception => current("fc1-fail"+e.getMessage)}
    Await.result(fc1,10 seconds)

    fc2 onSuccess{case x => current("fc2-success|"+x)}
    fc2 onFailure{case e:Exception => current("fc2-fail"+e.getMessage)}
    Await.result(fc2,10 seconds)
  }


}

object FuturesTest extends App{
  import Futures._
  chaining()
  callback()
}