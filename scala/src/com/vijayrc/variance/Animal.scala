package com.vijayrc.variance

class Animal {}
class Mammal extends Animal{}
class Dog extends Mammal{}

//class Box1 is covariant in T, Box[Dog] can be used for Box[Mammal] not Box[Animal] (Subtypes)
class Box1[+T]{}
//class Box1 is contravariant in T, Box[Animal] can be used for Box[Mammal] not Box[Dog] (Supertypes)
class Box2[-T]{}
//class Box3 is invariant in T
class Box3[T]{}


object Test extends App{
  def method1(box:Box1[Mammal]){}
  def method2(box:Box2[Mammal]){}
  def method3(box:Box3[Mammal]){}

  //method1(new Box1[Animal]) compile fails
  method1(new Box1[Mammal])
  method1(new Box1[Dog])

  method2(new Box2[Animal])
  method2(new Box2[Mammal])
  //method2(new Box2[Dog]) //compile fails

  //method3(new Box3[Animal]) //compile fails
  method3(new Box3[Mammal])
  //method3(new Box3[Dog]) //compile fails

}