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

  test("should match different pattern types"){

    //variable
    val expr = "vijay"
    val answer = expr match{
      case "shravan" => "its not the son"
      case "rekha" => "its not the son"
      case someoneElse => "its "+someoneElse
    }
    assert(answer =="its vijay")

    //sequence
    def listMatcher(list:Any):String = {
      list match{
        case List(_,2,_) => "the list has a 2"
        case List(0,_*) => "starts with 0"
        case _ => "we don't care"
      }
    }
    assert(listMatcher(List(1,2,3)) == "the list has a 2")
    assert(listMatcher(List(0,4,6,7,8)) == "starts with 0")

    //tuple
    val myTuple = (1,"1",Number(1))
    val tupleMatch: String = myTuple match {
      case (_, "1", expr: Expr) => "1" //not the type match here
      case (2, "1", Number(1)) => "2"
      case _ => "0"
    }
    assert(tupleMatch == "1")

    //type
    def generalSize(x: Any) = x match {
      case s: String => s.length
      case m: Map[_, _] => m.size
      case _ => -1
    }
    assert(generalSize("rekha") == 5)
    assert(generalSize(Map("1"->1,"2"->2)) == 2)
  }

  test("pattern guard"){
    val arr = Array(1,2,4)
    val matched: String = arr match {
      case Array(x,_*) if (x == 1) => "1"
      case Array(x,_*) if (x == 2) => "2"
      case _ => "-1"
    }
    assert (matched == "1")
  }

  test("Option to check for null"){
    val map = Map(1 -> "one",2 -> "two")
    def hasKey(value:Option[String]):String = {
      value match{
        case Some(x)=> x+" is present" //some string value is present and not null
        case None => "?" //null value
      }
    }
    hasKey(map get 1)
  }

  test("using match as a val"){
    //general function notation
    val family = (x:String) =>  x match  { case "rekha" => "wife" case "shravan" => "son" case _ => ""}

    //partial function
    val family2:String  => Int =  { case "rekha" => 1 case "shravan" => 2 case _ => -1}

    assert("wife" == family("rekha"))
    assert(1 == family2("rekha"))
  }





}
