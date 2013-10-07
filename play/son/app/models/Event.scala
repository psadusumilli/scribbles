package models

import org.joda.time.DateTime

case class Event (val name:String, title:String, html:String, time:DateTime)

object Event{
  def save(event:Event) ={}
  def all:List[Event] = {Nil}
}