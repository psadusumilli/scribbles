package models
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Location(val id:Long, val city:String, val state:String, val country:String)

object Location{
  def all(): List[Location] = DB.withConnection("diary") { implicit c =>
    SQL("select * from location").as(mapper *)
  }
  def byId(id: Long): Location = DB.withConnection("diary") {implicit c =>
    SQL("select * from location l where l.id={location_id}").on("location_id"->id).as(mapper *).head
  }
  val mapper = {
    get[Long]("id") ~
      get[String]("city") ~
      get[String]("state") ~
      get[String]("country") map{
      case id~city~state~country => Location(id,city,state,country)
    }
  }

}
