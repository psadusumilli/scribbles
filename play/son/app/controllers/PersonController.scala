package controllers
import play.api.mvc._
import actions.Authenticated
import play.api.data._
import play.api.data.Forms._
import models.{Image, Person}
import java.io.File

object PersonController extends Controller{
  def all = Authenticated{
    Action{Ok(views.html.persons(Person.all()))}
  }

  def create = Authenticated{
    Action{Ok(views.html.new_person(personForm))}
  }

  def submit = Authenticated{Action(parse.multipartFormData) {implicit request =>
      request.body.file("picture").map { picture =>
        val file: File = new File("/tmp/" + picture.filename)
        picture.ref.moveTo(file,replace = true)
        val image_id: Long = Image.save(file)
        file.delete()

        personForm.bindFromRequest.fold(
          errors => BadRequest(views.html.new_person(errors)),
          person =>Person.save(person.name,person.profile,image_id).toString
        )
      }
      Redirect(routes.PersonController.all())
    }
  }

  val personForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "profile" -> nonEmptyText)
      (PersonForm.apply)(PersonForm.unapply))
}
case class PersonForm(name:String,profile:String)
