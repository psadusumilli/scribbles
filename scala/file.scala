import scala.io.Source

def readFile(fileName:String) = 	Source.fromFile(fileName).getLines().foreach(println)
readFile(args(0))



