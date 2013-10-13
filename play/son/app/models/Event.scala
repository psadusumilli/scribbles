package models

import java.text.SimpleDateFormat
import java.util.Date

class Event (val id:Integer, val title:String,val location:String, val time:Date){
    private var html = ""
    private val df = new SimpleDateFormat("dd MMM yyyy")
    def addContent(h:String) {html = h}
    def content:String= {html}
    def date = df.format(time)
}

object Event{
  private val dateFormat = new SimpleDateFormat("dd/MM/yyyy")

  def findFor(name: String):Event = {
      null
  }

  def save(event:Event) ={}

  def all:List[Event] = {
    null
  }
}