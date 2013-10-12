package controllers

import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models.User
import play.api.Logger

object UserController extends Controller{
  val userForm = Form(
                    mapping("name" -> nonEmptyText,"password" -> nonEmptyText(6,12))
                    (User.apply)(User.unapply))

  def showLogin = Action{ implicit request =>
    Ok(views.html.login(userForm))
  }

  def login = Action{ implicit request =>
    userForm.bindFromRequest.fold(
      errors => BadRequest(views.html.login(errors)),
      user => {
        if (User.isValid(user)) {
          Logger.info("logged in: "+ user)
          Redirect(routes.EventController.all).withSession("user"->user.name)
        }
        else Unauthorized(views.html.login(userForm))
      }
    )
  }

  def logout = Action {
    Ok(views.html.login(userForm)).withNewSession
  }
}
