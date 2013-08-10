package com.vijayrc.function

class Functioner {

  def local(msg:String):String= {
    val suffix = "fucker"
    def prefix(msg:String):String = "Hey "+msg+" "+suffix
    prefix(msg)
  }

  def objects(numbers:List[Int]):List[Int] = {
    val sum = (x:Int,y:Int,z:Int) => x+y+z
    println("sum applied: "+sum.apply(1,2,3))
    val partialSum = sum(1,_:Int,4)
    println("partial: " +partialSum(2))

    val myfilter = (x:Int) => {x > 0 && x % 3 == 0}
    println("applying function object :"+myfilter.apply(4))
    numbers.filter(myfilter)
  }

  def closure(numbers:List[Int]):Int = {
    def appender(suffix:String) = (msg:String) => msg+" "+suffix+"!"
    val appender1 = appender("stupid") //appender1 stores suffix variable as "stupid"
    val appender2 = appender("dumbass")
    println(appender1("hey you"))
    println(appender2("hi there"))

    var sum = 0
    numbers.foreach(sum += _ ) //the function inside foreach is a closure, as it needs 'sum' var
    sum
  }

  def join(limiter:String,args:String*) = {
    var result=""
    args.foreach(arg => result+=arg+limiter)
    result
  }



}
