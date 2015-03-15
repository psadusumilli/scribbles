package com.vijayrc.oop


object Enums extends App{
  object WeekDay extends Enumeration {
    type WeekDay = Value
    val Mon = Value("Mon")
    val Tue = Value("Tue")
    val Wed = Value("Wed")
    val Sat = Value("Sat")
  }
  import WeekDay._
  println(WeekDay.Wed.toString)   // returns Fri
  def isWorkingDay(d: WeekDay) = d != Sat
  WeekDay.values filter isWorkingDay foreach println
}