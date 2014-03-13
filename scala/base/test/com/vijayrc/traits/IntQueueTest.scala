package com.vijayrc.traits

import org.scalatest.FunSuite

class IntQueueTest extends FunSuite{

  test("stackable traits"){
    val intQueue = new IntQueue
    intQueue.put(2)
    intQueue.put(3)
    assert(2 == intQueue.get())

    val intQueueWithDoubler = new IntQueue with Doubler
    intQueueWithDoubler.put(4)
    assert(8==intQueueWithDoubler.get())

    val myDoublerQueue = new MyDoublerQueue with Decrementer //rightmost trait executed first
    myDoublerQueue.put(10)
    assert(18 == myDoublerQueue.get())
  }

}
