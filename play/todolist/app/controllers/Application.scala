package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
//    Ok(views.html.index("Your new application is ready."))
    Ok("Hello Play")
  }

  def tasks = TODO
  def newTask = TODO
  def deleteTask(id:Long) = TODO

  
}