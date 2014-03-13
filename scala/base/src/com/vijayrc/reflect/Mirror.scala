package com.vijayrc.reflect

import scala.reflect.runtime.universe._


object Mirror extends App{
  /**
   * Symbols are used to establish bindings between a name and the entity it refers to, such as a class or a method.
   * TypeSymbols -  represents type, class, and trait declarations,
   * TermSymbols -   MethodSymbol (def), ModuleSymbol(obj)
   *
   * Type represent information about the type of a corresponding symbol
   *
   * Trees are the basis of Scala’s abstract syntax which is used to represent programs.
   * */
  def symbols(){
    val t1 = typeOf[List[Double]]
    println(t1)

    def getType[T: TypeTag](obj: T) = typeOf[T]
    println(getType(List(1,2,3)))

    val intTpe = definitions.IntTpe
    println(intTpe)

    //weak conformance
    println(typeOf[Int] weak_<:< typeOf[Double])
    println(typeOf[Double] weak_<:< typeOf[Int])

    val m  =  typeOf[List[_]].member("map": TermName)
    println(m.asMethod)
  }

  def trees(){
    val tree = Apply(Select(Ident(newTermName("x")), newTermName("$plus")), List(Literal(Constant(2))))
    println(show(tree))  //x.$plus(2)

    // reify simply takes the Scala expression it was passed, and returns a Scala Expr, which is simply wraps a Tree and a TypeTag
    val expr = reify { class Flower { def name = "Rose" } }
    println(show(expr.tree))
  }


  symbols()
  trees()

}
