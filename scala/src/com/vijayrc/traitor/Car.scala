package com.vijayrc.traitor

abstract class Car (val model:String, val make:String){
  override def toString: String = model+"|"+make
}
