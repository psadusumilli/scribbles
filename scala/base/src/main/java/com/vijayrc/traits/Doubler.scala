package com.vijayrc.traits

trait Doubler extends IntQueue{
  override def put(x: Int) {
    super.put(2*x)
  }
}
