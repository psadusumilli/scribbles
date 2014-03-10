package com.vijayrc.akka

import akka.actor._
import com.typesafe.config.ConfigFactory
import Util._

//TODO - selection not working, if selection is used, the 'deployment' in conf is not required
/** messages */
case class Package(content:String)
case class Reply(content:String)

/** local actor */
class LocalActor extends Actor with ActorLogging{
  private var peer:ActorRef = null

  override def preStart() {
   //peer = context.actorSelection("akka.tcp://system2@127.0.0.1:2552/user/my-remote-actor")
     peer = context.system.actorOf(Props[RemoteActor], "my-remote-actor")
    print(peer.toString())
  }
  def receive = {
    case x:Reply => log.info(x.toString)
    case x:Package => log.info(x.toString); peer ! x
  }
}
/** remote actor */
class RemoteActor extends Actor with ActorLogging{
  def receive = {
    case x:Package => log.info(x.toString); sender ! Reply("remote says hi")
  }
}
/** system1 contacting a actor from another system2(with remoting enabled)via remote address*/
object Remoting {
  def work(){
    val system2 = ActorSystem("system2", config("remote-application.conf"))
    val system1 = ActorSystem("system1",ConfigFactory.defaultOverrides())
    try {
      val remoteActor = system2.actorOf(Props[RemoteActor], "my-remote-actor")
      print(remoteActor.toString())
      val localActor = system1.actorOf(Props[LocalActor], "my-local-actor")
      localActor ! Package("local calling remote")
    }
    finally{
      Thread.sleep(4000)
      system1.shutdown()
      system2.shutdown()
    }
  }
}
/** test */
object RunSystem1 extends App{
  Remoting.work()
}
