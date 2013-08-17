package com.vijayrc.line

trait Doubler extends IntQueue{
  override def put(x: Int) {
    super.put(2*x)
  }
}
