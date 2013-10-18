package controllers

import play.api.mvc._
import models.{EventForm, Location, Event}
import actions.Authenticated
import play.api.data._
import play.api.data.Forms._

object EventController extends Controller {

  val eventForm = Form(
    mapping("title"->text(4,99),"content"->text(4,1000),"datetime"->nonEmptyText, "location_id"->longNumber, "person_ids"->list(longNumber))
    (EventForm.apply)(EventForm.unapply))
  
  def all = Authenticated{
    Action{Ok(views.html.events(Event.all))}
  }

  def get(id:Long) = Authenticated{
    Action{Ok(views.html.event(Event.byId(id)))}
  }

  def showNew = Authenticated{
    Action{Ok(views.html.new_event(Location.all(),ImageController.imageForm))}
  }
}