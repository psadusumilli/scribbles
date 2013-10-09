package models

import java.text.SimpleDateFormat
import java.util.Date

class Event (val name:String, val title:String,val location:String, val time:Date){
    private var html = ""
    private val df = new SimpleDateFormat("dd MMM yyyy")
    def addContent(h:String) {html = h}
    def content:String= {html}
    def date = df.format(time)
}

object Event{
  private val df = new SimpleDateFormat("dd/MM/yyyy")
  private val list:List[Event] = new Event("birth","Day of Entry","Vellore, TN, India",df.parse("21/11/2011"))::
    new Event("naming","Naming Ceremony","Vellore, TN, India",df.parse("11/12/2011"))::Nil

  val none: Event = new Event("404","Not found","",new Date())

  def findFor(name: String):Event = {
    val option = list.find(event => event.name == name)
    option match{
      case Some(x) => x
      case None => Event.none
    }
  }

  def save(event:Event) ={}

  def all:List[Event] = {
    list
  }
}