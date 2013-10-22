package models

import java.text.SimpleDateFormat
import java.util.Date
import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~
import play.api.Play.current
import controllers.EventForm
import play.Logger._

case class Event (id:Long, title:String, content:String, location:Location, dateTime:Date){
    def date = new SimpleDateFormat("dd MMM yyyy").format(dateTime)
}

object Event{
  def all:List[Event] = DB.withConnection("diary") { implicit c =>
    SQL("select * from event").as(dbRow *)
  }

  def save(eventForm:EventForm, person_ids:Seq[String]):Long = DB.withConnection("diary"){
    implicit c =>

    def saveEvent:Long = {
      val id: Option[Long] =
        SQL("insert into event(title,content,datetime,location_id) values ({title},{content},{datetime},{location_id})")
          .on('title -> eventForm.title,
          'content -> eventForm.content,
          'datetime -> eventForm.dateTime,
          'location_id -> eventForm.location_id).executeInsert()
      info("saved event:id=" + id.get + "|title=" + eventForm.title)
      id.get
    }

    val id: Long = saveEvent
    savePeople


    def savePeople {
      person_ids.map(person_id =>
        SQL("insert into event_person(event_id, person_id) values({event_id},{person_id})")
          .on('event_id -> id, 'person_id -> person_id).executeInsert()
      )
    }
    id
  }

  def byId(id: Long):Event = DB.withConnection("diary"){ implicit c =>
    SQL("select * from event e where e.id={event_id}").on("event_id"->id).as(dbRow *).head
  }

  val dbRow = {
    get[Long]("id") ~
    get[String]("title") ~
    get[String]("content") ~
    get[Date]("datetime") ~
    get[Long]("location_id") map{
    case id~title~content~datetime~location_id => Event(id,title,content,Location.byId(location_id),datetime)
    }
  }
}
