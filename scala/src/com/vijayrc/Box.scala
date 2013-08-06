package com.vijayrc

class Box {
    private var items:Set[Item] = Set()

    def add(item:Item):Unit = {
      items += item
    }

    def describe():String = {
      items.mkString(",")
    }

    def has(item:Item):Boolean = {
      items.exists(i => i.isSameAs(item))
    }
}
