package com.vijayrc.traitor

class FordFit extends Car("SubCompact","Ford") with GasGuzzler{
  override def consumeGas: String = "I am economical"
}
