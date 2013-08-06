package com.vijayrc

object Runner {

  def main(args:Array[String]){
    println("Hello boys")
    val basics = new Basics
    basics.conditional(4)
    basics.asserter()

    val pencil =  new Item
    pencil.make("pencil",2)
    val eraser =  new Item
    eraser.make("eraser",1)

    val box = new Box
    box.add(pencil)
    box.add(eraser)
    box.print()
  }




}
