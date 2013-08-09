package com.vijayrc.reason

class Rational(n:Int, d:Int){
  private val numero = n
  private val denom = d
  override def toString: String = numero + "/" + denom
  override def equals(obj: scala.Any): Boolean = {
    val that =  obj.asInstanceOf[Rational]
    this.denom ==  that.denom && this.numero == that.numero
  }
  def +(that:Rational) = new Rational(this.numero*that.denom + this.denom*that.numero,this.denom*that.denom)

}
