package com.vijayrc.akka

import com.typesafe.config.{ConfigFactory, Config}
import java.io.File


object Util {
  def getFile(name:String):String = {
    this.getClass.getResource("/"+name).getFile
  }

  def config(name:String):Config = {
    ConfigFactory.parseFile(new File(getFile(name)))
  }

}
