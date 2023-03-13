package GUI

import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination, KeyEvent, MouseEvent}
import jdk.jfr.Label
import logic.{Puzzle, Sodoku, Square}
import scalafx.scene.paint.Color
import scalafx.beans.property.*
import scalafx.scene.layout.{ColumnConstraints, GridPane, RowConstraints, StackPane}
import scalafx.scene.paint.Color.{Black, Blue, Brown, Gray, Red, White}
import scalafx.scene.{Parent, shape}
import scalafx.scene.shape.{ClosePath, HLineTo, LineTo, MoveTo, Path, Rectangle, VLineTo}
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.Includes.*
import scalafx.scene.shape.Line
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
def createDottedLine(pane:StackedSquare):Vector[Line]=
  var result:Vector[Line] = Vector()
  var square = pane.square
  var index  = square.position
  var neighboorSubArea = square.neighbor().filter(_.subArea.get == square.subArea.get).map(_.position)
  val bot = Line(0,0,squareLength,0)
  bot.alignmentInParent = Pos.BottomLeft
  result =result :+ bot

  val left =Line(0,0,0,squareLength)
  left.alignmentInParent = Pos.TopLeft
  result =result :+ left

  val right=Line(0,0,0,squareLength)
  right.alignmentInParent = Pos.TopRight
  result =result :+ right

  val top = Line(0,0,squareLength,0)
  top.alignmentInParent = Pos.TopLeft
  result =result :+ top
  result.foreach(_.visible = false)
  if !neighboorSubArea.contains(index-1) then
    left.visible = true
  if !neighboorSubArea.contains(index+1) then
    right.visible = true
  if !neighboorSubArea.contains(index+9) then
    bot.visible = true
  if !neighboorSubArea.contains(index-9) then
    top.visible = true

  result.foreach(_.strokeWidth = 5)
  result.foreach(_.getStrokeDashArray().addAll(0.1,7))
  result.foreach( pane.children.add(_))
  result.foreach(_.stroke = Brown.darker.darker)
  result



class NumberBox( pane:StackedSquare) extends Text :
    val square = pane.square
    pane.children.add(this)
    def update():Unit=
      if square.value < 10 && square.value > 0 then
        this.textProperty().update(""+square.value)
      else
        this.textProperty().update("")
    this.scaleX = 1.3
    this.scaleY = 1.3
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


def createPath(pane: StackedSquare): Vector[Line] =
  var result: Vector[Line] = Vector()
  var square = pane.square
  var rowindex = square.row.map(_.position)
  var columnindex = square.column.map(_.position)

  val left = Line(0, 0, 0, squareLength)
  left.alignmentInParent = Pos.TopLeft
  result = result :+ left

  val right = Line(0, 0, 0, squareLength)
  right.alignmentInParent = Pos.TopRight
  result = result :+ right

  val bot = Line(0, 0, squareLength, 0)
  bot.alignmentInParent = Pos.BottomLeft
  result = result :+ bot

  val top = Line(0, 0, squareLength, 0)
  top.alignmentInParent = Pos.TopLeft
  result = result :+ top

  result.foreach(_.strokeWidth = 1)
  result.foreach(pane.children.add(_))
  result.foreach(_.stroke = Black.brighter())

  if rowindex.forall(_ % 3 == 0) then
    top.strokeWidth = 3
  if rowindex.forall(_ % 3 == 2) then
    bot.strokeWidth = 3
  if columnindex.forall(_ % 3 == 0) then
    left.strokeWidth = 3
  if columnindex.forall(_ % 3 == 2) then
    right.strokeWidth = 3
  result



// create a StackPane that combine a square a path and a text box which represent Square's value
class StackedSquare(x:Int,y:Int,val gridPane: GridPane,bottomBar:Array[BottomStackPane]) extends StackPane :
    this.focusTraversable = true
    gridPane.add(this,x,y)
    // create and add components to the pane
    val square         = Sodoku.getSquare(x+y*9)
    val rect           = CreateRectangle(square.color,this)

    val text           = NumberBox(this)
    val path           = createPath(this)
    val dot             =createDottedLine(this)





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







