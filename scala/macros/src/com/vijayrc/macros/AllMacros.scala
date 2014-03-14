package com.vijayrc.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context
import scala.collection.mutable.ListBuffer
import scala.collection.mutable

/**
 * reify - makes a Expr[T] from a string code statement
 * splice - returns the T of an Expr[T]
 */
object AllMacros {
  //sample-1
  def hello(): Unit = macro hello_impl
  def hello_impl(c: Context)(): c.Expr[Unit] = {
    import c.universe._
    reify{println("hello world")}
  }

  //sample-2
  def printparam(param: Any): Unit = macro printparam_impl
  def printparam_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    reify { println(param.splice) }
  }

  //sample-3
  def debug(param: Any): Unit = macro debug_impl
  def debug_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    val paramRep = show(param.tree)
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)
    reify { println(paramRepExpr.splice + " = " + param.splice) }
  }

  //sample-4
  def printf(format: String, params: Any*): Unit = macro printf_impl
  def printf_impl(c: Context)(format: c.Expr[String], params: c.Expr[Any]*): c.Expr[Unit] = {
    import c.universe._

    val Literal(Constant(s_format: String)) = format.tree
    val evals = ListBuffer[ValDef]()

    def precompute(value: Tree, tpe: Type): Ident = {
      val freshName = newTermName(c.fresh("eval$"))
      evals += ValDef(Modifiers(), freshName, TypeTree(tpe), value)
      Ident(freshName)
    }
    val paramsStack = mutable.Stack[Tree](params map (_.tree): _*)
    val refs = s_format.split("(?<=%[\\w%])|(?=%[\\w%])") map {
      case "%d" => precompute(paramsStack.pop(), typeOf[Int])
      case "%s" => precompute(paramsStack.pop(), typeOf[String])
      case "%%" => Literal(Constant("%"))
      case part => Literal(Constant(part))
    }
    val stats = evals ++ refs.map(ref => reify(print(c.Expr[Any](ref).splice)).tree)
    c.Expr[Unit](Block(stats.toList, Literal(Constant(()))))
  }

  //sample-5
  def myif(cond:Boolean, yes:Any = {}, no:Any = {}) = macro myif_impl
  def myif_impl(c:Context)(cond:c.Expr[Boolean], yes:c.Expr[Any], no:c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    reify {
      if (cond.splice)
        yes.splice
      else
        no.splice
    }
  }
  def myif2(cond:Boolean, map:Map[String,Any]) = macro myif2_impl
  def myif2_impl(c:Context)(cond:c.Expr[Boolean], map:c.Expr[Map[String,Any]]): c.Expr[Unit] = {
    import c.universe._
    val paramRep = show(map.tree)
    val paramRepTree = Literal(Constant(paramRep))

    reify {
      if (cond.splice)
        print("yes")
      else
        print("no")

    }
  }




}
