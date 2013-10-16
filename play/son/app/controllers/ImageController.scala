package controllers

import play.api.mvc._
import actions.Authenticated
import play.api.mvc.Action
import java.io.File
import models.Image

object ImageController extends Controller{

  def upload = Authenticated{Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>
      val filename = picture.filename
      val file: File = new File("/tmp/" + filename)

      picture.ref.moveTo(file,true)
      Image.save(file)
      file.delete()

      Ok("File uploaded")
    }.getOrElse {
      Redirect(routes.EventController.all).flashing(
        "error" -> "Missing file"
      )
    }
   }
  }
}
