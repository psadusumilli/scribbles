package models

import java.text.SimpleDateFormat
import java.util.Date
import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~

case class Event (val id:Integer, val title:String, val content:String, val location:Location, val time:Date){
    private val df = new SimpleDateFormat("dd MMM yyyy")
    def date = df.format(time)
}

object Event{
  val mapper = {
    get[Integer]("id") ~
      get[String]("title") ~
      get[String]("content") ~
      get[Date]("datetime") ~
      get[Location]("location") map{
      case id~title~content~datetime~location => Event(id,title,content,location,datetime)
    }
  }

  def findFor(name: String):Event = {
      null
  }

  def save(event:Event) ={}

  def all:List[Event] = DB.withConnection { implicit c =>
    SQL("select * from event").as(mapper *)
  }
}
