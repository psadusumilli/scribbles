package com.vijayrc.inheritance

trait Speedster {
  def run  = "I run fast"
  def stop:String
  def willStop:Boolean = stop.contains("stop")

}
