package controllers

import play.api.mvc._
import actions.Authenticated
import models.Location

object LocationController extends Controller{
  def all = Authenticated{ Action{
    Ok(views.html.locations(Location.all))
    }
  }

  def create = Authenticated{ Action{
    Ok("create")
    }
  }

  def submit = Authenticated{ Action{
    Ok("submit")
    }
  }

}
