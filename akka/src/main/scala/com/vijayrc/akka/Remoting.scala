package com.vijayrc.akka

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import Util._

/** system1 contacting a actor from another system2(with remoting enabled)via remote address*/
object Remoting {
  def runSystem1(){
    val system1 = ActorSystem("system1",ConfigFactory.defaultOverrides())
    system1.logConfiguration()
  }

  def runSystem2(){
    val system2 = ActorSystem("system2", config("remote-application.conf"))
    system2.logConfiguration()
  }

}

object RunSystem1 extends App{
  Remoting.runSystem1()
}

object RunSystem2 extends App{
  Remoting.runSystem2()
}
