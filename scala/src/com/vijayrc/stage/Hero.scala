package com.vijayrc.stage

import scala.actors.Actor

class Hero extends Actor{
  def act() {
      println("hero entry scene..")
      while (true){
        receive{
          case dialogue => println("hero says: "+dialogue)
        }

      }

  }
}
