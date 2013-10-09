package controllers

import play.api.mvc._
import models.Event

object EventController extends Controller {
  
  def all = Action{
    Ok(views.html.events(Event.all))
  }

  def get(name:String) = Action{
    Ok(views.html.event(Event.findFor(name)))
  }
}