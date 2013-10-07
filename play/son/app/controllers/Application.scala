package controllers

import play.api._
import play.api.mvc._
import models.Event

object Application extends Controller {
  
  def index = Action {
    Redirect(routes.Application.all)
  }

  def all = Action{
    Ok(views.html.index(Event.all))
  }
  
}