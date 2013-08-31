package com.vijayrc.collect

class MyList{

  def append(first:List[Int], second:List[Int]):List[Int] = {
      first match{
        case List() => second
        case head::tail => head::append(tail,second)
      }
  }

}
