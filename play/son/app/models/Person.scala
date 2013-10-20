package models

import play.api.db._
import anorm._
import anorm.SqlParser._
import play.api.Play.current

case class Person (id:Long,name:String, profile:String,image:Image){
  def imageUrl:String =  "/image/"+image.id
}

object Person{

  def all():List[Person] = DB.withConnection("diary"){implicit c =>
    SQL("select * from person").as(dbRow *)
  }

  def save(name: String, profile: String, image_id: Long): Long = DB.withConnection("diary"){implicit c =>
    val id: Option[Long] = SQL("insert into person(name,profile,image_id) values ({name},{profile},{image_id})")
      .on('name -> name).on('profile -> profile).on('image_id -> image_id).executeInsert()
    id.get
  }

  val dbRow = {
    get[Long]("id")~
    get[String]("name")~
    get[String]("profile")~
    get[Long]("image_id") map{
      case id~name~profile~image_id => Person(id,name,profile,Image.byId(image_id))
    }
  }
}

