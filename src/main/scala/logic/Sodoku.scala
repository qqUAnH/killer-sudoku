package logic

import GUI.StackedSquare
import IO.JSON

import scala.collection.immutable
import scala.collection.mutable.Buffer
import os.Path

object Sodoku extends App {
  private val invoker= Invoker()
  private var puzzle = Puzzle()
  //some thing have changed
  def getPuzzle = this.puzzle

  def getSquare(index :Int) = this.puzzle.square(index)

  def setValue(pane: StackedSquare, value:Int) =
    invoker.setValue(pane,value)

  def undo() =
    invoker.undo()

  def redo() =
    invoker.redo()
  def load(path:Path) =
    val data = IO.JSON.load(path)
    if data.isDefined then
      this.puzzle.setUpPuzzle2(data.get)
    else
      println("Invalid File")

  load(JSON.saveFolder / "savefile2.txt")

  def save( path: Path) = JSON.save(this.getPuzzle.allSubAreas(),path)


  val x = this.puzzle.allSquare().filter( x=> x.row.isDefined && x.column.isDefined && x.subArea.isDefined ).length
  @main def test =
    println( this.puzzle.allSubAreas().map( x=>x.numberOfPossibleCombination(x.numberOfEmptySquares,x.alphabet,x.currentSum)))

}
