package com.vijayrc.akka

import akka.actor.{ActorSystem, ActorLogging, Actor}
import akka.testkit.TestActorRef
import org.scalatest.FunSuite


class MyActor extends Actor with ActorLogging{
  def receive = {
    case "ok" =>
  }
  def say = {"hi"}
}
class Tester extends FunSuite{
  test("should work in synch mode"){
    implicit val system = ActorSystem.create("system1")
    val testRef = TestActorRef[MyActor]
    val testActor = testRef.underlyingActor
    assert(testActor.say == "hi")
  }
}
