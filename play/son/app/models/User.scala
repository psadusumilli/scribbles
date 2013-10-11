package models

case class User(val name:String, val password:String) {

  override def equals(obj: scala.Any): Boolean = {
    val other = obj match{
      case x:User => x
      case _ => {return false}
    }
    other.name == name && other.password == password
  }

  override def toString: String = name+"|"+password
}
object User{
  def isValid(user:User):Boolean = {
    //TODO write code to fetch from DB
    val dbUser = new User("father","password")
    return user.equals(dbUser)
  }
}