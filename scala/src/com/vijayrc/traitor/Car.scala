package com.vijayrc.traitor

abstract class Car (model:String, val make:String){
  override def toString: String = model+"|"+make
}
