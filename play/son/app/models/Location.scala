package models
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Location(val id:Integer, val city:String, val state:String, val country:String)

object Location{
  val mapper = {
    get[Integer]("id") ~
    get[String]("city") ~
    get[String]("state") ~
    get[String]("country") map{
      case id~city~state~country => Location(id,city,state,country)
    }
  }

  def all(): List[Location] = DB.withConnection { implicit c =>
    SQL("select * from location").as(mapper *)
  }

}
