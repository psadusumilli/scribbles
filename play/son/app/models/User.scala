package models

import play.api.db.DB
import play.api.Play.current

case class User(name:String, password:String)

object User{
  def isValid(user:User):Boolean = {
    val dbUser = new User("father","password")
    DB.withConnection("diary"){ conn =>
     }
    user.equals(dbUser)
  }
}