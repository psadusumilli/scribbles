package controllers

import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models.User
import play.api.Logger

object UserController extends Controller{
  def home = Action{ implicit request => Ok(views.html.login(userForm))}

  def login = Action{ implicit request =>
    userForm.bindFromRequest.fold(
        errors => BadRequest(views.html.login(errors)),
        user => {
          if (User.isNotValid(user.name, user.password))
            Unauthorized(views.html.login(userForm))
          else{
            Logger.info("login: "+ user.name)
            Redirect(routes.EventController.all()).withSession("user"->user.name)
          }
        }
    )
  }

  def logout = Action {Ok(views.html.login(userForm)).withNewSession}

  val userForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "password" -> nonEmptyText(6,12))
      (UserForm.apply)(UserForm.unapply))
}
case class UserForm(name:String, password:String)