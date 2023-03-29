package logic

import GUI.SquareNode
import logic.{SetValueCommand, Square}

import scala.collection.mutable.Stack






class Invoker() {
  private val undoStack:Stack[SetValueCommand]=Stack[SetValueCommand]()

  private val redoStack:Stack[SetValueCommand]=Stack[SetValueCommand]()

  def setValue(pane: SquareNode, newValue:Int) =
    redoStack.popAll()
    val command =SetValueCommand(pane,pane.square.value,newValue)
    // change redo to execute
    command.execute()
    undoStack.push(command)

  def undo() =
    if undoStack.nonEmpty then
     undoStack.head.undo()
     redoStack.push(undoStack.head)
     undoStack.pop

  def redo() =
    if redoStack.nonEmpty then
      redoStack.head.redo()
      undoStack.push(redoStack.head)
      redoStack.pop
}
