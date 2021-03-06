package models

import java.sql.{Blob, PreparedStatement}
import java.io.{InputStream, FileInputStream, File}
import anorm._
import play.Logger._
import play.api.db._
import play.api.Play.current
import java.sql
import scala.collection.mutable.ListBuffer
import anorm.SqlParser._

case class Image(id:Long,name:String,content:Blob)

object Image{

  def byPersonId(person_id: Long): Image = DB.withConnection("diary"){ implicit c =>
    val image_id: Long = SQL("select img_id from person where id={person_id}").on('person_id -> person_id).as(personIdRow *).head
    Image.byId(image_id)
  }

  def byId(id: Long): Image = DB.withConnection("diary"){ implicit c =>
    val stmt:PreparedStatement = c.prepareStatement("select * from image where id="+id)
    val rs: sql.ResultSet = stmt.executeQuery()
    val images = new ListBuffer[Image]()
    while(rs.next())
      images += new Image(rs.getLong("id"),rs.getString("name"),rs.getBlob("content"))
    rs.close()
    stmt.close()
    images.head
  }

  def save(file:File):Long = DB.withConnection("diary"){implicit c =>
    val idOption: Option[Long]  = SQL("insert into image (name) values ({name})").on('name -> file.getName).executeInsert()
    val id = idOption.get
    val inputStream:InputStream = new FileInputStream(file)
    val stmt:PreparedStatement = c.prepareStatement("update image set content=? where id="+id)

    stmt.setBinaryStream(1,inputStream,file.length())
    stmt.executeUpdate();
    inputStream.close()
    info("saved image: "+file.getName+" with id: "+id)
    id
  }

  val personIdRow = {
    get[Long]("img_id") map{case img_id => img_id }
  }

}
