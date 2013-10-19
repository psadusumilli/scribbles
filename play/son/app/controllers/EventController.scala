package controllers

import play.api.mvc._
import models.{Location, Event}
import actions.Authenticated
import play.api.data._
import play.api.data.Forms._

object EventController extends Controller {
  def all = Authenticated{
    Action{Ok(views.html.events(Event.all))}
  }

  def get(id:Long) = Authenticated{
    Action{Ok(views.html.event(Event.byId(id)))}
  }

  def create = Authenticated{
    Action{Ok(views.html.new_event(Location.all()))}
  }

  def submit = Authenticated{
    Action{Ok("submitted event")}
  }

  val eventForm = Form(
    mapping(
      "title"->text(4,99),
      "content"->text(4,1000),
      "datetime"->nonEmptyText,
      "location_id"->longNumber,
      "person_ids"->list(longNumber))
      (EventForm.apply)(EventForm.unapply))
}
case class EventForm(title:String, content:String, dateTime:String, location_id:Long, person_ids:List[Long])
