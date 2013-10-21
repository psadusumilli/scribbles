package controllers

import play.api.mvc._
import models.{Person, Location, Event}
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
    Action{Ok(views.html.new_event(Location.all(),Person.all(),eventForm))}
  }

  def submit = Authenticated{
    Action{implicit request =>
      eventForm.bindFromRequest().fold(
        errors => BadRequest(views.html.new_event(Location.all(),Person.all(),errors)),//TODO
        event => Event.save(event)
      )
      Ok(views.html.events(Event.all))
    }
  }

  val eventForm = Form(
    mapping(
      "title"->nonEmptyText,
      "content"->nonEmptyText,
      "datetime"->nonEmptyText,
      "location_id"->longNumber,
      "person_ids"->list(longNumber))
      (EventForm.apply)(EventForm.unapply))
}
case class EventForm(title:String, content:String, dateTime:String, location_id:Long, person_ids:List[Long])
