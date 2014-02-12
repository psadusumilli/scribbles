package com.vijayrc.akka

import akka.actor._
import akka.event.Logging

/*holds on to the peer actor-ref a1*/
class DummyContext(val actorRef:ActorRef){
}
/*creates actor a2 instance factory, will be a spring application context if used, see 'bean-name'*/
class Injector(applicationContext: AnyRef, beanName: String) extends IndirectActorProducer {
  override def actorClass = classOf[Actor]
  override def produce() = {
    val peer = applicationContext.asInstanceOf[DummyContext].actorRef
    new A2(peer)
  }
}
/*actor1*/
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
/*actor2 will send integer message to actor1 in another actorsystem*/
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

object TwoSystems {
  def work(){
    val system1 = ActorSystem.create("system1")
    val system2 = ActorSystem.create("system2")
    try {
      val actor1 = system1.actorOf(Props[A1],"actor1")
      val dummy = new DummyContext(actor1)
      val actor2 = system2.actorOf(Props(classOf[Injector], dummy, "actor2"),"dummyBean")

      actor1 ! "msg1"
      actor2 ! "msg2"
      actor2 ! 2
    } finally {
      system1.shutdown()
      system2.shutdown()
    }
  }

}
object Test2 extends App{
  TwoSystems.work()
}