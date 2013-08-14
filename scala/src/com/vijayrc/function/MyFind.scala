package com.vijayrc.function

import java.io.File

class MyFind {
  //plugin method 'matchfunction' def goes here
  def find(filePath:String,query:String,matchFunction:(String,String)=>Boolean):Array[File] = {
      val files = new File(filePath).listFiles()
      for(file <- files if matchFunction(file.getName,query))
        yield file
  }

  //plugin method 'matchfunction' def goes here
  def find(filePath:String,matchFunction:(String)=>Boolean):Array[File] = {
    val files = new File(filePath).listFiles()
    for(file <- files if matchFunction(file.getName))
    yield file
  }

  //'matchfunction' implementation goes here
  def filesEndingWith(filePath:String, query:String):Array[File] = {
    find(filePath,query,_.endsWith(_))
  }

  //'matchfunction' implementation goes here with 2 bound variables
  def filesContaining(filePath:String, query:String):Array[File] = {
    find(filePath,query,_.contains(_))
  }

  //matchfunction implementation has one bound variable and 1 free regex variable
  def filesRegex(filePath:String, regex:String):Array[File] = {
    find(filePath,_.matches(regex))
  }

}
