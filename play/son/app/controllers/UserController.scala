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
    userForm.bindFromRequest.fold(
      errors => BadRequest(views.html.login(errors)),
      user =>{
        print(user)
        if (User.isValid(user)) Redirect(routes.EventController.all).withSession("user"->user.name)
        else BadRequest(views.html.login(userForm))
      }
    )
  }


}
