package controllers
import play.api.mvc._
import actions.Authenticated
import play.api.data._
import play.api.data.Forms._
import models.Person

object PersonController extends Controller{
  def all = Authenticated{
    Action{Ok(views.html.persons(Person.all))}
  }

  def create = Authenticated{
    Action{Ok(views.html.new_person(personForm))}
  }

  def submit = Authenticated{
    Action{ implicit request =>
      personForm.bindFromRequest.fold(
      errors => BadRequest(views.html.new_person(errors)),
      person => Redirect(routes.EventController.all)
      )
    }
  }

  val personForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "profile" -> nonEmptyText(6,12),
      "img_id" -> longNumber)
      (PersonForm.apply)(PersonForm.unapply))
}
case class PersonForm(name:String,profile:String,img_id:Long)
