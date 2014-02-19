package com.vijayrc.akka

import akka.actor._
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._
import akka.actor.OneForOneStrategy

/**parent actor*/
class Parent extends Actor with ActorLogging{
  log.info("parent|constructor called")
  spawn()

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1.minute) {
      case _: ArithmeticException      ⇒ Resume
      case _: NullPointerException     ⇒ Restart
      case _: IllegalArgumentException ⇒ Stop
      case _: Exception                ⇒ Escalate
    }

  def receive={
    case "error" => throw new Exception("parent")

    case "c1-error" => context.child("c1").get ! "error"
    case "c1-art-error" => context.child("c1").get ! "art-error"
    case "c1-null-error" => context.child("c1").get ! "null-error"
    case "c1-illegal-error" => context.child("c1").get ! "illegal-error"
    case "c1-message" => context.child("c1").get ! "message"

    case "c2-error" => context.child("c2").get ! "error"
    case x => print(x)
  }

  def spawn(){
    context.actorOf(Props.create(classOf[Child], "c1-title"), "c1")
//    context.actorOf(Props.create(classOf[Child], "c2-title"), "c2")
  }
  override def preStart() {
    log.info("parent|prestart")
    super.preStart()
  }
  override def postStop() {
    log.info("parent|postStop")
    super.postStop()
  }
  override def preRestart(reason: Throwable, message: Option[Any]) {
    log.info("parent|preRestart")
    super.preRestart(reason, message)
  }
  override def postRestart(reason: Throwable) {
    log.info("parent|postRestart")
    super.postRestart(reason)
  }
}

/** child actor*/
class Child(val title:String) extends Actor with ActorLogging{
  log.info(title+"|constructor called")

  def receive={
    case "error" => throw new Exception(title+"|error|should escalate")
    case "art-error" => throw new ArithmeticException(title+"|arithmetic|should resume")
    case "null-error" => throw new NullPointerException(title+"|nullpointer|should restart")
    case "illegal-error" => throw new IllegalArgumentException(title+"|illegal|should stop")
    case x:String => log.info(title+"|"+x)
  }
  override def preStart() {
    log.info(title+"|prestart")
    super.preStart()
  }
  override def postStop() {
    log.info(title+"|postStop")
    super.postStop()
  }
  override def preRestart(reason: Throwable, message: Option[Any]) {
    log.info(title+"|preRestart")
    super.preRestart(reason, message)
  }
  override def postRestart(reason: Throwable) {
    log.info(title+"|postRestart")
    super.postRestart(reason)
  }
}

/** try all scenarios of failure */
object OnError {
  def work(){
    val system = ActorSystem.create("system")
    def shouldRestart(parent: ActorRef) {
      parent ! "c1-null-error"
      parent ! "c1-message"
      Thread.sleep(2000)
    }
    def shouldResume(parent: ActorRef) {
      parent ! "c1-art-error"
      parent ! "c1-message"
      Thread.sleep(2000)
    }
    def shouldEscalate(parent: ActorRef) {
      parent ! "c1-error"
      parent ! "c1-message"
      Thread.sleep(2000)
    }
    def shouldStop(parent: ActorRef) {
      parent ! "c1-illegal-error"
      parent ! "c1-message"
      Thread.sleep(2000)
    }
    try {
      val parent = system.actorOf(Props[Parent], "parent")
      //shouldResume(parent)
      //shouldRestart(parent)
      //shouldEscalate(parent)
      shouldStop(parent)
      //parent ! "c2-error"
    }
    finally {
      Thread.sleep(6000)
      system.shutdown()
    }
  }
}

object OnErrorTest extends App{
  OnError.work()
}
