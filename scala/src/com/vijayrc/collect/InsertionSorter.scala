package com.vijayrc.collect

class InsertionSorter {

   def sort(input:List):List ={

    return null;
  }

  def recurse(head:Ordered[Any], tail:List[Ordered[Any]]):List ={
    tail.foreach(x => if (x.compare(head) > 1))
  }

}
