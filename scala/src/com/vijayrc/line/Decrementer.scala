package com.vijayrc.line

trait Decrementer extends IntQueue{
  override def put(x: Int) {
    super.put(x-1)
  }
}
