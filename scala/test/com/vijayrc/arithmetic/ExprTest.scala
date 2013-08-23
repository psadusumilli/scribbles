package com.vijayrc.arithmetic

import org.scalatest.FunSuite

class ExprTest extends FunSuite{

  test("basics of case class"){
    val v1 = Variable("v1")
    assert(v1.name == "v1")
    val v2 = Variable("v2")
    val n1 = Number(1)
    val n2 = Number(1)

    val expr1 = BiOp("+",v2,n1)
    val expr2 = BiOp("+",v2,n2)
    print(expr1,"  ",expr2)
    assert(expr1.equals(expr2))
  }

  test("do pattern matching"){
    def simplify(expr:Expr):Expr = {
      expr match {
        case BiOp("+",e,Number(0))=> e //e+0
        case BiOp("*",e,Number(1))=> e //e*1
        case UnOp("-",UnOp("-",e))=> e //-(-e)
        case BiOp("^",e,Number(1)) => e //e to the power 1
        case _ => expr
      }
    }
    val expr = BiOp("^",Variable("x"),Number(1))
    val simplified: Expr = simplify(expr)
    assert(simplified == Variable("x"))
  }


}
