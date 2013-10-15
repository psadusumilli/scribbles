package models

import java.text.SimpleDateFormat
import java.util.Date
import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~
import play.api.Play.current

case class Event (val id:Integer, val title:String, val content:String, val location:Location, val dateTime:Date){
    private val df = new SimpleDateFormat("dd MMM yyyy")
    def date = df.format(dateTime)
}

object Event{
//  val mapper = {
//      get[Long]("id") ~
//      get[String]("title") ~
//      get[String]("content") ~
//      get[Date]("datetime") ~
//      get[Location]("location") map{
//      case id~title~content~datetime~location => Event(id,title,content,location,datetime)
//    }
//  }

  def findFor(name: String):Event = {
      null
  }

  def save(event:Event) ={}

  def all:List[Event] = DB.withConnection("diary") { implicit c =>
    //SQL("select * from event").as(mapper *)
    null
  }
}
