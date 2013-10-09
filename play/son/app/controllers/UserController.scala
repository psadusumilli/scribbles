package controllers

import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models.User

object UserController extends Controller{
  val userForm = Form(
                    mapping("name" -> nonEmptyText,"password" -> nonEmptyText(6,12))
                    (User.apply)(User.unapply))

  def showLogin = Action{ implicit request =>
    Ok(views.html.login(userForm))
  }

  def login = Action{ implicit request =>
    val user = userForm.bindFromRequest.get
    print(user)
    Redirect(routes.EventController.all)
  }

}
