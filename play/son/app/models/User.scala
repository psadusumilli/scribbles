package models


import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~
import play.api.Play.current

case class User(id:Long,name:String, password:String)

object User{
  def isNotValid(name:String, password:String):Boolean = DB.withConnection("diary"){ implicit c =>
    val dbUsers = SQL("select * from admin where name = {name}").on('name -> name).as(dbRow *)
    dbUsers.isEmpty || dbUsers.head.password != password
  }

  val dbRow = {
    get[Long]("id")~
    get[String]("name") ~
    get[String]("password") map{ case id~name~password => User(id,name,password)}
  }

}