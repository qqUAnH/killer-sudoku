package Game
import logic.*

object Game extends App {
  
  def readfile() = Array.tabulate(81)(x => x+1)
  
  private val puzzle = Puzzle()
  
  this.puzzle.setUpPuzzle(readfile())

  println(this.puzzle.allcolumns().map( row => row.map(square => square.value).mkString(",")).mkString("\n"))

  this.puzzle.square(20).setValue(9999)


}
