package controllers

import play.api.mvc._
import actions.Authenticated
import play.api.mvc.Action
import models.Image
import play.api.libs.iteratee.Enumerator

object ImageController extends Controller{
  def show(id:Long) = Authenticated{Action{ implicit response =>
      val image: Image = Image.byId(id)
      Ok.stream(Enumerator.fromStream(image.content.getBinaryStream))
    }
  }
  def showPerson(id:Long) = Authenticated{Action{ implicit response =>
      val image: Image = Image.byPersonId(id)
      Ok.stream(Enumerator.fromStream(image.content.getBinaryStream))
    }
  }
}
