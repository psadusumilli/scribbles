package com.vijayrc.akka

import akka.actor._
import akka.event.Logging

class DummyContext(val actorRef:ActorRef){
  def actorBean = actorRef
}
class DependencyInjector(applicationContext: AnyRef, beanName: String) extends IndirectActorProducer {
  override def actorClass = classOf[Actor]
  override def produce() = {
    val peer = applicationContext.asInstanceOf[DummyContext].actorBean
    new A2(peer)
  }
}
class A1 extends Actor{
  val log = Logging(context.system, this)

  def receive = {
    case x:String => log.info(x)
    case y:Int => log.info(y.toString)
  }
  override def postStop() {
    log.info("stopping")
    super.postStop()
  }
}
class A2(val peer:ActorRef) extends Actor{
  val log = Logging(context.system, this)

  def receive = {
    case x:String => log.info(x)
    case y:Int => {peer ! y}
  }
  override def postStop() {
    log.info("stopping")
    super.postStop()
  }
}

class TwoSystems {

  def work(){
    val system1 = ActorSystem.create("system1")
    val system2 = ActorSystem.create("system2")
    try {
      val actor1 = system1.actorOf(Props[A1],"actor1")

      val dummy = new DummyContext(actor1)
      val actor2 = system2.actorOf(Props(classOf[DependencyInjector], dummy, "actor2"),"dummyBean")

      actor1 ! "msg1"
      actor2 ! "msg2"
      actor2 ! 2
    }
    finally {
      system1.shutdown()
      system2.shutdown()
    }
  }

}
object Test2 extends App{
  new TwoSystems().work()
}