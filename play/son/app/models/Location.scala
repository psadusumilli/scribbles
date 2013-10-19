package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Location(id:Long,city:String,state:String,country:String){
  def name:String = city+" "+state+" "+country
}

object Location{
  def all(): List[Location] = DB.withConnection("diary") { implicit c =>
    SQL("select * from location").as(dbRow *)
  }
  def byId(id: Long): Location = DB.withConnection("diary") {implicit c =>
    SQL("select * from location l where l.id={location_id}").on("location_id"->id).as(dbRow *).head
  }
  val dbRow = {
    get[Long]("id") ~
    get[String]("city") ~
    get[String]("state") ~
    get[String]("country") map{
    case id~city~state~country => Location(id,city,state,country)
    }
  }

}
