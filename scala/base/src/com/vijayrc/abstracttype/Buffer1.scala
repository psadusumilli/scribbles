package com.vijayrc.abstracttype

//declares a abstract type T
trait Buffer1 {
  type T
  val element: T
}
//declares a subtype which declares another type U and constrains T to be a subtype pf Seq[U]
abstract class SeqBuffer1 extends Buffer1 {
  type U
  type T <: Seq[U]
  def length = element.length
}
//making abstract type U as Int
abstract class IntSeqBuffer1 extends SeqBuffer1 {
  type U = Int
}
object Test1 extends App {
  def newIntSeqBuf(elem1: Int, elem2: Int): IntSeqBuffer1 =
    new IntSeqBuffer1 {
      type T = List[U]
      val element = List(elem1, elem2)
    }
  val buf = newIntSeqBuf(7, 8)
  println("length = " + buf.length)
  println("content = " + buf.element)
}
