package com.vijayrc.traits

trait Decrementer extends IntQueue{
  override def put(x: Int) {
    super.put(x-1)
  }
}
