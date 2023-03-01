package Game
import logic.*

import scala.collection.immutable
import scala.collection.mutable.Buffer

object Game extends App {
  def readfile() = Array.tabulate(81)(x => x+1)
  // this input have problem
  def readsubarea() = Buffer( Vector(5,1,2), Vector( 11,3,12) ,Vector(9,4,5),
                              Vector(18,6,15,16),Vector(12,7,8,17),Vector(22,9,18,27,26),
                              Vector(15,10,19),Vector(6,11,20),Vector(10,21,22),
                              Vector(15,13,14,23),Vector(12,24,25),
                              Vector(12,28,37),Vector(14,29,30),Vector(3,31,32),Vector(9,33,34),
                              Vector(15,35,36),Vector(9,38,38+9),Vector(6,39,40),Vector(16,41,42),
    Vector(16,43,43+9,43+9-1,43+9-2),Vector(14,44,44+9),Vector(21,45,45+9,45+18,45+17),
    Vector(9,46,46+9),Vector(7,48,49),Vector(16,56,57),Vector(31,58,58+9,58+18,58+10,58+19),
    Vector(4,59,60),Vector(21,61,61+9,61+8),Vector(18,64,65,64+9,65+9),Vector(9,66,66+9),
    Vector(3,71,72),Vector(8,80,81),Vector(9,78,79)
  )

  val puzzle = Puzzle()
  
  this.puzzle.setUpPuzzle(readfile(),readsubarea())

  println(this.puzzle.allcolumns().map( row => row.map(square => square.value).mkString(",")).mkString("\n"))
  if this.puzzle.allSquare().exists( _.subArea ==null ) then
    println( this.puzzle.allSquare().filter(_.subArea == null).map(_.position).mkString(" "))

  else
    println("yay")
    println( this.puzzle.allSquare().map(x=>""+ x.neighbor().length ).mkString(" "))
    println( this.puzzle.allSubAreas().map(x=>x.neigbor.length).mkString(" "))
    println( this.puzzle.allSubAreas().map(x=>x.squares.length).mkString(" "))
    println(""+ this.puzzle.allSquare().length)

  def isWin() :Boolean = ???

}
