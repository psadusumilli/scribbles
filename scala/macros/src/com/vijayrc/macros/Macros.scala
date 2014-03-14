package com.vijayrc.macros

import scala.reflect.macros.Context

object Macros {
  sealed trait Flow
  case object then extends Flow
  case object otherwise extends Flow

  def myif(cond:Boolean, partial:PartialFunction[Flow,Any]) = macro myif_impl
  def myif_impl(c:Context)(cond:c.Expr[Boolean], partial:c.Expr[PartialFunction[Flow,Any]]): c.Expr[Unit] = {
    import c.universe._
    reify {
      try {
        if (cond.splice) partial.splice(then)
        else partial.splice(otherwise)
      } catch {case e:MatchError => {}}
    }
  }
}
