package logic
import GUI.SquareNode
import scala.collection.mutable.Stack

trait Command {
  def undo():Unit
  def redo():Unit
  def execute():Unit
}

sealed case class SetValueCommand(pane: SquareNode, oldValue :Int, newValue :Int) extends Command:
  def undo():Unit =

    pane.square.setValue(oldValue)
    pane.numberBox.update()
    pane.rect.update1()
    pane.requestFocus()

  def redo():Unit =
    pane.square.setValue(newValue)
    pane.numberBox.update()
    pane.rect.update1()
    pane.requestFocus()

  def execute():Unit =
    pane.square.setValue(newValue)
    pane.requestFocus()
