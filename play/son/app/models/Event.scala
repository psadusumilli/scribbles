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
import scala.collection.parallel.mutable
import scala.collection.mutable.ListBuffer

case class Event (id:Long, title:String, content:String, location:Location, dateTime:Date){
    val images = ListBuffer[Long]()
    val persons = ListBuffer[Long]()

    def addPerson(person_ids:List[Long]) {person_ids.map{person_id => persons += person_id} }
    def addImage(image_ids:List[Long]) = {image_ids.map{image_id => images += image_id}}
    def date = new SimpleDateFormat("dd MMM yyyy").format(dateTime)
}

object Event{
  def all:List[Event] = DB.withConnection("diary") { implicit c =>
    SQL("select * from event").as(dbRow *)
  }

  def save(eventForm:EventForm, person_ids:Seq[String], image_id:Long):Long = DB.withConnection("diary"){
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
    val event_id: Long = saveEvent
    person_ids.map(person_id =>
      SQL("insert into event_person(event_id, person_id) values({event_id},{person_id})")
      .on('event_id -> event_id, 'person_id -> person_id).executeInsert()
    )
    SQL("insert into event_image(event_id, img_id) values({event_id},{img_id})")
    .on('event_id -> event_id, 'img_id -> image_id).executeInsert()

    event_id
  }

  def byId(id: Long):Event = DB.withConnection("diary"){ implicit c =>
    val event: Event = SQL("select * from event e where e.id={event_id}").on("event_id" -> id).as(dbRow *).head
    event.addImage(SQL("select img_id from event_image where event_id={event_id}").on("event_id" -> id).as(imageIdRow *))
    event.addPerson(SQL("select person_id from event_person where event_id={event_id}").on("event_id" -> id).as(personIdRow *))
    event
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

  val imageIdRow = {
    get[Long]("img_id") map{case image_id => image_id}
  }
  val personIdRow = {
    get[Long]("person_id") map{case person_id => person_id }
  }

}
