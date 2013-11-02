package com.vijayrc.algos.queue

import org.scalatest.FunSuite

class QueueTest extends FunSuite{

  test("should work for a link based queue"){
    val queue:Queue = new QueueLinks
    for(i <- 1 to 5)queue.enqueue(i)
    for(i <- 1 to 2)queue.dequeue()
    queue.show()
  }

}
