package com.vijayrc.reason

class Rational(n:Int, d:Int) extends Ordered[Rational]{
  require(d != 0)
  private val numero = n
  private val denom = d

  override def toString: String = numero + "/" + denom
  override def equals(obj: scala.Any): Boolean = {
    val that =  obj.asInstanceOf[Rational]
    this.denom ==  that.denom && this.numero == that.numero
  }

  def this(n:Int) = this(n,1)
  def +(that:Rational) = new Rational(this.numero*that.denom + this.denom*that.numero,this.denom*that.denom)
  def compare(that: Rational): Int = this.numero*that.denom - this.denom*that.numero
}
