package com.vijayrc.function

class Functioner {
  def say(msg:String):String= {
    val suffix = "fucker"
    def prefix(msg:String):String = "Hey "+msg+" "+suffix
    prefix(msg)
  }

  def filter(numbers:List[Int]):List[Int] = {
    val myfilter = (x:Int) => {x > 0 && x % 3 == 0}
    println("applying function object :"+myfilter.apply(4))
    numbers.filter(myfilter)
  }

}
