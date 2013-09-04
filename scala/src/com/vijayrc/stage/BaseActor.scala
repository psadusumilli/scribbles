package com.vijayrc.stage

import scala.actors.Actor

abstract class BaseActor(val name:String) extends Actor{
  def getName = name
}
