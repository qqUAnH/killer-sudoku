package Game
import logic.*
import scala.collection.mutable.Buffer

object Game extends App {
  
  def readfile() = Array.tabulate(81)(x => x+1)

  def readsubarea() = Buffer( Vector(5,1,2), Vector( 11,3,12) ,Vector(9,4,5),Vector(18,6,15,16),Vector(12,7,8,17),Vector(22,9,18,27,26),Vector(15,10,19),Vector(6,11,20),Vector(10,21,22),Vector(15,13,14,23),Vector(12,24,25))
  
  
  val puzzle = Puzzle()
  
  this.puzzle.setUpPuzzle(readfile())

  println(this.puzzle.allcolumns().map( row => row.map(square => square.value).mkString(",")).mkString("\n"))

  this.puzzle.square(20).setValue(9999)



}
