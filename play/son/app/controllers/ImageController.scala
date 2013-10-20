package controllers

import play.api.mvc._
import actions.Authenticated
import play.api.mvc.Action
import java.io.File
import models.Image

object ImageController extends Controller{

  def upload = Authenticated{Action(parse.multipartFormData) { implicit request =>
    request.body.file("picture").map { picture =>
      val filename = picture.filename
      val file: File = new File("/tmp/" + filename)
      picture.ref.moveTo(file,true)
      val id: Long = Image.save(file)
      file.delete()
      Ok(id.toString)
    }.getOrElse {
      //TODO got to change the image error routing
      Redirect(routes.EventController.all()).flashing("error" -> "Missing file")
    }
   }
  }

  def show(id:Long) = Authenticated{Action{
    Ok("image will be shown")
    }
  }

}
