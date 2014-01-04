package com.vijayrc.actors

import scala.actors.Actor
import org.scalatest.FunSuite
import scala.collection.mutable

trait MyActor extends Actor{
  private var threadLoads = Map[String, Int]()
  private var mailboxSizes = new mutable.MutableList[Int]
  private var execCount = 0

  protected def track(msg:Any) {
    val name = Thread.currentThread().getName
    if(!threadLoads.contains(name)) threadLoads += (name -> 0)
    threadLoads += (name -> (threadLoads(name) + 1))
    mailboxSizes += mailboxSize
  }
  def print(){
    mailboxSizes.foreach(println)
    threadLoads.foreach(println)
    println("mailbox-size="+mailboxSize+"|execCount="+execCount+"|actor state="+getState+"|active count="+Thread.activeCount())
  }
  protected def increment(){ execCount+=1 }
}
class LoopReceiveActor extends MyActor{
  def act() {
    loop{
      receive{
        case msg => track(msg)
      }
      increment()
    }
  }
}
class WhileReceiveActor extends MyActor{
  def act() {
    while(true){
      receive{
        case msg => track(msg)
      }
      increment()
    }
  }
}
class LoopReactActor extends MyActor{
  def act() {
    loop{
      react{
        case msg => track(msg)
      }
      increment()
    }
  }
}
class WhileReactActor extends MyActor{
  def act() {
    while(true){
      react{
        case msg => track(msg)
      }
      increment()
    }
  }
}

class StageTest extends FunSuite{
  test("see what happens for a while-receive-actor"){testRun(new WhileReceiveActor)} // 1 thread, 20000 msgs
  test("see what happens for a loop-receive-actor"){testRun(new LoopReceiveActor)} // many threads, 20000 msgs
  test("see what happens for a while-react-actor"){testRun(new WhileReactActor)} //1 thread, 1 msg
  test("see what happens for a loop-react-actor"){testRun(new LoopReactActor)}//many threads, 20000 msgs

  def testRun(actor: MyActor) {
    actor.start()
    for (i <- 1 until 20000) actor ! "msg-" + i
    Thread.sleep(3000)
    actor.print()
  }
}

