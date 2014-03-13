package com.vijayrc.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

/**
 * reify - makes a Expr[T] from a code statement
 * splice - returns the T of an Expr[T]
 *
 */
object AllMacros {
  def hello(): Unit = macro hello_impl
  def hello_impl(c: Context)(): c.Expr[Unit] = {
    import c.universe._
    reify{println("hello world")}
  }

  def printparam(param: Any): Unit = macro printparam_impl
  def printparam_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    reify { println(param.splice) }
  }

  def debug(param: Any): Unit = macro debug_impl

  def debug_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    val paramRep = show(param.tree)
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)
    reify { println(paramRepExpr.splice + " = " + param.splice) }
  }

}
