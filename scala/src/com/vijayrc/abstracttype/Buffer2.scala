package com.vijayrc.abstracttype

//abstract type T declared
abstract class Buffer2[+T] {
  val element: T
}
//the sub type declares another abstract type U and constraining T be a subclass of Seq[U]
abstract class SeqBuffer2[U, +T <: Seq[U]] extends Buffer2[T] {
  def length = element.length
}

object Test2 extends App {
  def newIntSeqBuf(e1: Int, e2: Int): SeqBuffer2[Int, Seq[Int]] =
    new SeqBuffer2[Int, List[Int]] { //U,Seq[U]
      val element = List(e1, e2)
    }
  val buf = newIntSeqBuf(7, 8)
  println("length = " + buf.length)
  println("content = " + buf.element)
}