package models

import java.text.SimpleDateFormat
import java.util.Date

class Event (val name:String, val title:String,val location:String, val time:Date){
    private var html = ""
    def addContent(h:String) {html = h}
    def content:String= {html}
}

object Event{
  def save(event:Event) ={}
  def all:List[Event] = {
    val df = new SimpleDateFormat("dd/MM/yyyy");
    val event1 = new Event("birth","Day of Entry","Vellore, TN, India",df.parse("21/11/2011"))
    val event2 = new Event("naming","Naming Ceremony","Vellore, TN, India",df.parse("11/12/2011"))
    event1::event2::Nil
  }
}