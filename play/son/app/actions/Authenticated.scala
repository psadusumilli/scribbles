package actions

import play.api.mvc._

case class Authenticated[A](action:Action[A]) extends Action[A]{
  def apply(request: Request[A]): Result = {
    request.session.get("user").map{
      user => {
        println("authenticated: ",user)
        action(request)
      }
    } getOrElse{
        Results.Forbidden
    }
  }

  def parser: BodyParser[A] = {return action.parser}
}
