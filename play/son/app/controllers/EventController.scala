package controllers

import play.api.mvc._
import models.Event
import actions.Authenticated

object EventController extends Controller {
  
  def all = Authenticated{
    Action{Ok(views.html.events(Event.all))}
  }

  def get(name:String) = Authenticated{
    Action{Ok(views.html.event(Event.findFor(name)))}
  }
}