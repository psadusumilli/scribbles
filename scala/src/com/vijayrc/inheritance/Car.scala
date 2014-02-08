package com.vijayrc.inheritance

abstract class Car (model:String, val make:String){
  override def toString: String = model+"|"+make
}
