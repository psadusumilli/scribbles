package controllers

import play.api.mvc._
import actions.Authenticated
import play.api.mvc.Action
import play.Logger
import java.io.File

object ImageController extends Controller{

  def upload = Authenticated{Action(parse.multipartFormData) { request =>
    request.body.file("picture").map { picture =>
      val filename = picture.filename
      val contentType = picture.contentType
      Logger.info(filename+" "+contentType)

      picture.ref.moveTo(new File("/home/vijayrc/pics/picture.jpg"))
      Ok("File uploaded")
    }.getOrElse {
      Redirect(routes.EventController.all).flashing(
        "error" -> "Missing file"
      )
    }
   }
  }
}
