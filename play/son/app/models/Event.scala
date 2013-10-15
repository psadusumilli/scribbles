package models

import java.text.SimpleDateFormat
import java.util.Date
import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~
import play.api.Play.current

case class Event (id:Long, title:String, content:String, location:Location, dateTime:Date){
    def date = new SimpleDateFormat("dd MMM yyyy").format(dateTime)
}
case class EventForm(title:String, content:String, dateTime:String, location_id:Long, person_ids:List[Long])

object Event{
  def all:List[Event] = DB.withConnection("diary") { implicit c =>
    SQL("select * from event").as(mapper *)
  }

  def save(eventForm:EventForm):Long ={
     print(eventForm.title+" "+eventForm.person_ids.size)
     val id: Option[Long] = SQL("insert into event(title,content,datetime,location_id) values ({title},{content},{datetime},{location_id})")
      .on('title -> eventForm.title, 'content -> eventForm.content,'datetime -> eventForm.dateTime, 'location_id -> eventForm.location_id).executeInsert()
     id.get
  }

  def byId(id: Long):Event = {
    SQL("select * from event e where e.id={event_id}").on("event_id"->id).as(mapper *).head
  }

  val mapper = {
    get[Long]("id") ~
      get[String]("title") ~
      get[String]("content") ~
      get[Date]("datetime") ~
      get[Long]("location_id") map{
      case id~title~content~datetime~location => Event(id,title,content,Location.byId(location),datetime)
    }
  }
}
