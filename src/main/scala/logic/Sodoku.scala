package logic

import GUI.SquareNode
import IO.JSON

import scala.collection.immutable
import scala.collection.mutable.Buffer
import os.Path

object Sodoku extends App {
  private var invoker= Invoker()
  val puzzle = Puzzle()

  //shoud getPuzzle be removed
  //def getPuzzle = this.puzzle


  def getSquare(index :Int) = this.puzzle.square(index)

  def setValue(pane: SquareNode, value:Int) =
    invoker.setValue(pane,value)

  def undo() =
    invoker.undo()

  def redo() =
    invoker.redo()

  //@TODO:should create new invoker!! or Invoker change to object
  def load(path:Path) =
    val data = IO.JSON.load(path)
    if data.isDefined then
      this.puzzle.setUpPuzzle(data.get)
      this.invoker = new Invoker
    else
      println("Invalid File")

  load(JSON.saveFolder / "savefile2.txt")

 //TODO:Migrate this to INvoker ?
  def save( path: Path) = 
    JSON.save(puzzle.getSquares.map(_.getSubArea.get).distinct.toBuffer ,path)








}
