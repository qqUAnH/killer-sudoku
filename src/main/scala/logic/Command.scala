package logic

import GUI.StackedSquare

import scala.collection.mutable.Stack

trait Command {
  def undo():Unit
  def redo():Unit
  def execute():Unit
}

case class SetValueCommand(pane: StackedSquare, oldValue :Int, newValue :Int) extends Command:
  def undo():Unit =
    pane.requestFocus()
    pane.square.setValue(oldValue)
    pane.text.update()

  def redo():Unit =
    pane.requestFocus()
    pane.square.setValue(newValue)
    pane.text.update()

  def execute():Unit =
    pane.requestFocus()
    pane.square.setValue(newValue)
    pane.text.update()


object SetValueCommandStack:
  val undoStack:Stack[SetValueCommand]=Stack[SetValueCommand]()

  val redoStack:Stack[SetValueCommand]=Stack[SetValueCommand]()