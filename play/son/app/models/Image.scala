package models

import java.sql.{PreparedStatement, Clob}
import java.io.{InputStream, FileInputStream, File}
import anorm._
import play.Logger
import play.api.db._
import play.api.Play.current

case class Image(id:Long,name:String,content:Clob)
case class ImageForm(name:String)

object Image{
  def save(file:File):Long = DB.withConnection("diary"){implicit c =>
    val idOption: Option[Long]  = SQL("insert into image (name) values ({name})").on('name -> file.getName).executeInsert()
    val id = idOption.get
    val i:InputStream = new FileInputStream(file)
    val stmt:PreparedStatement = c.prepareStatement("update image set content=? where id="+id)

    stmt.setBinaryStream(1,i,file.length())
    stmt.executeUpdate();
    i.close()
    Logger.info("saved image: "+file.getName+" with id: "+id)
    id
  }
}
