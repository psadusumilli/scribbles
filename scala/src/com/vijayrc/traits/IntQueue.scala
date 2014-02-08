package com.vijayrc.traits

import scala.collection.mutable.ArrayBuffer

class IntQueue extends AbstractQueue{
  private val buf = new ArrayBuffer[Int]
  def put(x: Int) {buf += x }
  def get(): Int = buf.remove(0)
}
