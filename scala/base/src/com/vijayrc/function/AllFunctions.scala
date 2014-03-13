package com.vijayrc.function

class AllFunctions {

  def local(msg:String):String= {
    val suffix = "fucker"
    def prefix(msg:String):String = "Hey "+msg+" "+suffix
    prefix(msg)
  }

  def objects(numbers:List[Int]):List[Int] = {
    val sum = (x:Int,y:Int,z:Int) => x+y+z
    println("sum applied: "+sum.apply(1,2,3))
    val partialSum = sum(1,_:Int,4) // _ is placeholder
    println("partial: " +partialSum(2))

    val myfilter = (x:Int) => {x > 0 && x % 3 == 0}

    println("applying function object :"+myfilter.apply(4))
    println("does numbers contains -ve number?" +numbers.exists( _ < 0))
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

  def closureScopes(){
    //filter elements < (first element - offset)
    //function isBelow has 2 free variables, first, offset
    //'first' is it's first surrounding function 'belowFirst'
    //'offset' is the second surrounding function 'closureScopes'
    val offset = 3
    val belowFirst  =  ( inputList : List[Int] )  =>  {
      val first = inputList( 0 )
      val isBelow  =  ( y : Int )  =>   y < (first + offset)
      for(  x <- inputList;  if  isBelow( x ))  yield x
    }
    print(belowFirst(List(4,21,1,4,2,3)))

  }

  def repeatedArg(limiter:String,args:String*) = {
    var result=""
    args.foreach(arg => result+=arg+limiter)
    result
  }

  def recursion(number:Int):Int = {
    if(number == 0) 1 else number * recursion(number - 1)
  }

  def currying(){
    def incrBy(increment:Int)(number:Int) = number+increment
    val incrByTwo = incrBy(2)_
    val incrByOne = incrBy(1)_
    println(incrBy(1)(2),incrByTwo(3),incrByOne(1))

    //{} can be used instead of () for method args if there is only one arg or the last arg in a curry
    println {"hey there curly"}

    def myTest(desc:String)(testFun: => Unit){
      println("executing test:"+desc)
      testFun
    }
    myTest("dummy one on one"){
      assert(1==1)
    }
  }

  def parameterized[T](x: T, n: Int): List[T] = {
    if (n == 0)
      Nil
    else
      x :: parameterized(x, n - 1)
  }
}
