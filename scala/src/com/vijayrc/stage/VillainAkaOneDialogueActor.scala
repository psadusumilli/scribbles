package com.vijayrc.stage


class VillainAkaOneDialogueActor extends BaseActor("VILLAIN=>") {
    def act(){
      println(name+"started")
      react{
        //internally, a exception is thrown at this point, so the calling thread never incurs this method stack
        //since there is no loop, this actor will say only one dialogue (execute only once in its lifetime)
        case dialogue => println (name+dialogue)
      }
    }
}
