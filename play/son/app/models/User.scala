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
