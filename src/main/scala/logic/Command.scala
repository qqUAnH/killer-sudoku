package logic
import GUI.StackedSquare
import scala.collection.mutable.Stack

trait Command {
  def undo():Unit
  def redo():Unit
  def execute():Unit
}

sealed case class SetValueCommand(pane: StackedSquare, oldValue :Int, newValue :Int) extends Command:
  def undo():Unit =
    pane.requestFocus()
    pane.square.setValue(oldValue)
    pane.numberBox.update()

  def redo():Unit =
    pane.requestFocus()
    pane.square.setValue(newValue)
    pane.numberBox.update()

  def execute():Unit =
    pane.requestFocus()
    pane.square.setValue(newValue)
    pane.numberBox.update()
