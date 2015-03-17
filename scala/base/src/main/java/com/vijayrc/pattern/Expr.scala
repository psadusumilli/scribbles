package com.vijayrc.pattern

abstract class Expr
case class Number(value:Double) extends Expr
case class Variable(name:String) extends Expr
case class UnOp(operator:String,arg:Expr) extends Expr
case class BiOp(operator:String,left:Expr,right:Expr) extends Expr

