package com.vijayrc.algos.probs

import com.vijayrc.algos.stack.{StackArray, Stack}

class Dijkstra2Stack {
  private val valueStack:Stack = new StackArray(4)
  private val operatorStack:Stack = new StackArray(4)

  private def isDigit(c: Char): Boolean = {
    var flag = false
    (0 to 9).foreach(i => if(c == i.toString.charAt(0)) flag = true)
    flag
  }
  private def isOperator(c: Char): Boolean = {
    var flag = false
    Array('*','+','/','-').foreach(i => if(c == i) flag = true)
    flag
  }
  def evaluate(expr:String):Int = {
    var flag = false //multi-digit numbers flag, is previous character digit
    expr.foreach(c =>
      if(isDigit(c)){
        if(flag) valueStack.push(valueStack.pop()+""+c)
        else valueStack.push(c)
        flag = true
      }
      else if(isOperator(c)) {flag = false; operatorStack.push(c)}
      else if(c == '('){flag = false}
      else if(c == ')'){
        flag = false
        val a = valueStack.pop()
        val b = valueStack.pop()
        val op = operatorStack.pop()
        valueStack.push(doOps(a,b,op))
      }else{
        println("unknown char: "+c)
      }
    )
    valueStack.pop().toString.toInt
  }

  private def doOps(a:Any, b:Any, op:Any):Int = {
    op match{
        case '*' => b.toString.toInt * a.toString.toInt
        case '+' => b.toString.toInt + a.toString.toInt
        case '-' => b.toString.toInt - a.toString.toInt
        case '/' => b.toString.toInt / a.toString.toInt
    }
  }

}
