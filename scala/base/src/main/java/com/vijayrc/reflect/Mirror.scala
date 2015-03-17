package com.vijayrc.reflect

import scala.reflect.runtime.universe._

/**
 * A)Symbols are used to establish bindings between a name and the entity it refers to, such as a class or a method.
 * Symbols have hierarchy
 *
 * 1) TypeSymbols: represents type, class, and trait declarations -
 *  1.1) ClassSymbol (isFinal, isPrivate, isProtected, isAbstractClass)
 * 2) TermSymbols:
 *  2.1) MethodSymbol (def declarations - (primary) constructor, or whether a method supports variable-length argument lists)
 *  2.2) ModuleSymbol (object declarattions - )
 * --------------------------------------------------------------------------
 * B)Type represent information about the type of a corresponding Symbol
 * A Type’s members include all fields, methods, type aliases, abstract types, nested classes/objects/traits, etc.
 * --------------------------------------------------------------------------
 * C)Trees are the basis of Scala’s abstract syntax which is used to represent programs.
 *
 *  1) Subclasses of [TermTree] which represent terms,
 *  e.g., method invocations are represented by [Apply] nodes, object instantiation is achieved using [New] nodes, etc.
 *
 *  2) Subclasses of [TypTree] which represent types that are explicitly specified in program source code,
 *  e.g., List[Int] is parsed as AppliedTypeTree.
 *
 *  3) Subclasses of [SymTree] which introduce or reference definitions.
 *  Examples of the introduction of new definitions include
 *    [ClassDefs] which represent class and trait definitions, or
 *    [ValDef] which represent field and parameter definitions.
 *    [Idents] which refer to an existing definition in the current scope such as a local variable or a method.
 *
 *  M1) reify() simply takes the Scala String expression, and returns a Scala Expr that simply wraps a Tree and a TypeTag
 *  M2) show
 *
 * */

object Mirror extends App{
  class C[T] { def test[U](x: T)(y: U): Int = ??? }

  def symbols(){
    val member = typeOf[C[Int]].member(newTermName("test"))
    println(member)//method test - which is a MethodSymbol
    println(member.isType)//false
  }

  def types(){
    val t1 = typeOf[List[Double]]
    println("t1="+t1)//List[Double]
    println("t1="+t1.typeSymbol)//class List

    def getType[T: TypeTag](obj: T) = typeOf[T]
    println("type using instance="+getType(List(1,2,3)))

    //standard types
    val intTpe = definitions.IntTpe
    println("int type="+intTpe)

    //weak conformance
    println(typeOf[Int] weak_<:< typeOf[Double])
    println(typeOf[Double] weak_<:< typeOf[Int])

    //querying Types to get symbols of its members
    val m1  =  typeOf[List[_]].member("map": TermName)
    val m2  =  typeOf[List[_]].declaration("map": TermName)//no map method on List but present on its supertypes
    println("m.asMethod="+m1.asMethod)
    println("m.asMethod="+m2)
  }

  def trees(){
    val tree = Apply(Select(Ident(newTermName("x")), newTermName("$plus")), List(Literal(Constant(2))))
    println(show(tree))  //x.$plus(2)

    val expr = reify { class Flower { def name = "Rose" } }
    println(show(expr.tree))
  }

  //symbols()
 //types()
  trees()

}
