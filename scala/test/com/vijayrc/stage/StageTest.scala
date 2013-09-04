package com.vijayrc.stage

import org.scalatest.FunSuite

class StageTest extends FunSuite{

  test("act everyone act.."){
    val hero = new HeroAkaBlockingActor
    hero.start()
    hero.!("I'll punch you in the face")
    hero !5

    val villain = new VillainAkaOneDialogueActor
    villain.start()
    villain ! "fuck you!"
    villain ! "double fuck you!"

    val heroine = new HeroineAkaReactingActor
    heroine.start()
    heroine ! ("love you darling", hero)
    Thread.sleep(10000) //wait for all actors to respond before closing test main thread

  }

}
