package GUI

import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination, KeyEvent, MouseEvent}
import jdk.jfr.Label
import logic.{Puzzle, Sodoku, Square}
import scalafx.scene.paint.Color
import scalafx.beans.property.*
import scalafx.scene.layout.{ColumnConstraints, GridPane, RowConstraints, StackPane}
import scalafx.scene.paint.Color.{Black, Blue, Gray, Red, White}
import scalafx.scene.{Parent, shape}
import scalafx.scene.shape.{HLineTo, MoveTo, Path, Rectangle, VLineTo}
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.Includes.*
import logic.Sodoku


// Create a reactangle which change color when its parent node is hovered
def CreateRectangle( color:Color , pane: StackPane ) :shape.Rectangle =
  new shape.Rectangle() :
    pane.children.add(this)
    fill=color
    x = 0
    y = 0
    width = squareLength
    height = squareLength
    var i = 0
    parent.value.hoverProperty.onChange((_, _, _) =>
      if i == 0 then
        i=1
        this.fill.update(color.darker())
      else
        i=0
        this.fill.update(color))
end CreateRectangle


// Create a closed path which can be used to as a boundary of a node
def createPath() = new Path:
  this.elements.addAll(MoveTo(0,0),VLineTo(squareLength),HLineTo(squareLength),VLineTo(0),HLineTo(0))
end createPath




class NumberBox( pane:StackedSquare) extends Text :
    val square = pane.square
    pane.children.add(this)
    def update():Unit=
      if square.value < 10 && square.value > 0 then
        this.textProperty().update(""+square.value)
      else
        this.textProperty().update("")

    this.update()
    pane.onKeyPressed = (ke:KeyEvent) => {
      ke.getCode match
        case KeyCode.DIGIT1     => Sodoku.setValue(pane,1)
        case KeyCode.DIGIT2     => Sodoku.setValue(pane,2)
        case KeyCode.DIGIT3     => Sodoku.setValue(pane,3)
        case KeyCode.DIGIT4     => Sodoku.setValue(pane,4)
        case KeyCode.DIGIT5     => Sodoku.setValue(pane,5)
        case KeyCode.DIGIT6     => Sodoku.setValue(pane,6)
        case KeyCode.DIGIT7     => Sodoku.setValue(pane,7)
        case KeyCode.DIGIT8     => Sodoku.setValue(pane,8)
        case KeyCode.DIGIT9     => Sodoku.setValue(pane,9)
        case KeyCode.BACK_SPACE => Sodoku.setValue(pane,0)
        case KeyCode.Z          => Sodoku.undo()
        case KeyCode.Y          => Sodoku.redo()
        case _                  => println("throw song")
      this.update()
    }




// create a StackPane that combine a square a path and a text box which represent Square's value
class StackedSquare(x:Int,y:Int,val gridPane: GridPane,bottomBar:Array[BottomStackPane]) extends StackPane :
    this.focusTraversable = true
    gridPane.add(this,x,y)
    // create and add components to the pane
    val square         = Sodoku.getSquare(x+y*9)
    val rect           = CreateRectangle(square.color,this)
    val path           = createPath()
    val text           = NumberBox(this)
    this.children.add(path)

    if square.isFirstSquare && square.subArea.isDefined then
      val sumText = new Text(""+ square.subArea.get.sum)
      sumText.alignmentInParent = Pos.TopLeft
      this.children.add(sumText)

    this.onMouseClicked = (m:MouseEvent) => {
      bottomBar.foreach(pane=> pane.updateColor(square.possibleNumbers.contains(pane.number)))
      bottomBar.foreach(_.requestFocus())
      this.requestFocus()
      m.consume()
    }
end StackedSquare

class BottomStackPane( index:Int) extends StackPane():
  val number     = index+1
  val candidate = new Text(""+(number))
  candidate.scaleX = 1.3
  candidate.scaleY = 1.3

  // change color of candidate :Text to Read if the candidate number is according to the game rule , black otherwise )
  def updateColor(possible: Boolean): Unit =
    if possible then
      candidate.fill = Black
    else
      candidate.fill = White

  this.alignment = Pos.Center

  val rectangle = new Rectangle:
    width = squareLength
    height =squareLength
    fill =Gray

  this.children.addAll(rectangle)
  this.children.add(candidate)







