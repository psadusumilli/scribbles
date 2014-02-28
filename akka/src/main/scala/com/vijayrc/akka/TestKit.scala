package com.vijayrc.akka

import akka.actor.{ActorSystem, ActorLogging, Actor}
import akka.testkit.TestActorRef
import org.scalatest.FunSuite
import akka.pattern.ask
import scala.concurrent.Future
import scala.concurrent.duration._
import akka.util.Timeout
import scala.util.Try

class MyActor extends Actor with ActorLogging{
  def receive = {
    case "hi" => sender ! say
    case "throw" ⇒ throw new IllegalArgumentException("boom")
  }
  def say = {"ok"}
}
/**
 *  Messages sent to the actor are processed synchronously on the current thread and answers may be sent back as usual.
 *  TestActorRef overwrites two fields:
 *    it sets the dispatcher to CallingThreadDispatcher.global
 *    it sets the receiveTimeout to None.
 */
class Tester extends FunSuite{

  test("should work in synch mode"){
    implicit val system = ActorSystem.create("system1")
    implicit val timeout = Timeout(5 seconds)

    //get original actor
    val testRef = TestActorRef[MyActor]
    val testActor = testRef.underlyingActor
    assert(testActor.say == "ok")

    //using synch dispatcher
    val future: Future[Any] = testRef ? "hi"
    val result: Try[Any] = future.value.get
    assert(result.get == "ok")

    //direct call to receive
    intercept[IllegalArgumentException] { testRef.receive("throw") }
  }
}
