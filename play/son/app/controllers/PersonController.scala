package controllers
import play.api.mvc._
import actions.Authenticated
import play.api.data._
import play.api.data.Forms._
import models.PersonForm

object PersonController extends Controller{

  val personForm = Form(
    mapping("name" -> nonEmptyText,"profile" -> nonEmptyText(6,12),"image_id" -> longNumber)
      (PersonForm.apply)(PersonForm.unapply))

  def showCreate = Authenticated{
    Action{Ok(views.html.new_person(personForm))}
  }

  def create = Authenticated{
    Action{ implicit request =>
      personForm.bindFromRequest.fold(
        errors => BadRequest(views.html.new_person(errors)),
        person => Redirect(routes.EventController.all)
      )
    }
  }

}
