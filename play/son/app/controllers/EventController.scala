package controllers

import play.api.mvc._
import models.Event

object EventController extends Controller {
  
  def index = Action {
    Redirect(routes.EventController.all)
  }

  def all = Action{
    Ok(views.html.index(Event.all))
  }

  def get(name:String) = Action{
    Ok(views.html.event(Event.findFor(name)))
  }
}