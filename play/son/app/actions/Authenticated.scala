package actions

import play.api.mvc._
import scala.concurrent.Future

class AuthenticatedRequest[A](val username:String, request:Request[A]) extends WrappedRequest[A](request)

object Authenticated extends ActionBuilder{

  def invokeBlock[A](request:Request[A], block:(AuthenticatedRequest[A]) => Future[SimpleResult[A]]) = {
    request.session.get("user").map{
      user => block(new AuthenticatedRequest[A](user, request))
    } getOrElse{
      Future.successful(Results.Forbidden)
    }
  }
}
