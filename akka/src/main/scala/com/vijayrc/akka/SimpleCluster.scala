package com.vijayrc.akka

import akka.actor.{Props, ActorSystem, Actor, ActorLogging}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._


class Listener extends Actor with ActorLogging {
  def receive = {
    case state: CurrentClusterState => log.info("Current members: {}", state.members.mkString(", "))
    case MemberUp(member) => log.info("Member is Up: {}", member.address)
    case MemberRemoved(member) =>  log.info("Member is Removed: {} after {}", member.address)
    case _: ClusterDomainEvent ⇒ log.info("unknown event")
  }
}

object SimpleCluster extends App{
    System.setProperty("akka.remote.netty.tcp.port", "2551")
    val system = ActorSystem("system")
    val listener = system.actorOf(Props[Listener],name = "listener")
    Cluster(system).subscribe(listener, classOf[ClusterDomainEvent])
}