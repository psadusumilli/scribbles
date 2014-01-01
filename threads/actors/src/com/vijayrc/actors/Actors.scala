package com.vijayrc.actors

import scala.actors.Actor
import org.scalatest.FunSuite

/**
 * RECEIVE
 * When the receive primitive is used, the actor is internally backed by a dedicated thread.
 * This obviously limits scalability and requires the thread to suspend and block when waiting for new messages.
 *
 * REACT
 * The react primitive allows and event-driven execution strategy,
 * which does not directly couple actors to threads.
 * Instead, a thread pool can be used for a number of actors.
 * This approach uses a continuation closure to encapsulate the actor and its state
 */
trait MyActor extends Actor{
  protected var threads = Map[String, Int]()

  protected def track(msg:Any) {
    val name = Thread.currentThread().getName
    if(!threads.contains(name)) threads += (name -> 0)
    threads += (name -> (threads(name) + 1))
  }
  def print(){threads.foreach(println)}
}
class LoopReceiveActor extends MyActor{
  def act() {
    loop{
      receive{
        case msg => track(msg)
      }
    }
  }
}
class WhileReceiveActor extends MyActor{
  def act() {
    while(true){
      receive{
        case msg => track(msg)
      }
    }
  }
}
class LoopReactActor extends MyActor{
  def act() {
    loop{
      react{
        case msg => track(msg)
      }
    }
  }
}
class WhileReactActor extends MyActor{
  def act() {
    while(true){
      react{
        case msg => track(msg)
      }
    }
  }
}

class StageTest extends FunSuite{
  test("see what happens for a while-receive-actor"){testRun(new WhileReceiveActor)}
  test("see what happens for a loop-receive-actor"){testRun(new LoopReceiveActor)}
  test("see what happens for a while-react-actor"){testRun(new WhileReactActor)}
  test("see what happens for a loop-react-actor"){testRun(new LoopReactActor)}

  def testRun(actor: MyActor) {
    actor.start()
    for (i <- 1 until 20000) actor ! "msg-" + i
    Thread.sleep(3000)
    actor.print()
  }
}

