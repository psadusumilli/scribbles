class Item {
	private var name = ""
        private var quantity = 0
      
        def make(x: String, y:Int):Unit = {
		name = x
                quantity = y
	}	    
   	def show() = println(name+"--"+quantity)
}
class Bag {
	var items:Set[Item] = Set() 
  
  	def addItem( item: Item ):Unit = items+=item
	def display():Unit = {
          	for(item <- items)
			item.show()
	}
}
val bag = new Bag
val item1 = new Item
item1.make("pencil",2)
val item2 = new Item
item2.make("eraser",1)

bag.addItem(item1)
bag.addItem(item2)
bag.display()



