package com.vijayrc.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

object MacroMan {
  def hello(): Unit = macro hello_impl

  def hello_impl(c: Context)(): c.Expr[Unit] = {
    import c.universe._
    reify{println("hello world")}
  }

}
