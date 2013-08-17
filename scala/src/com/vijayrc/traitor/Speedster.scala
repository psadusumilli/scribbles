package com.vijayrc.traitor

trait Speedster {
  def run  = "I run fast"
  def stop:String
  def willStop:Boolean = stop.contains("stop")

}
