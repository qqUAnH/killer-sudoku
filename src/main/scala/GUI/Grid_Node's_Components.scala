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


/**
 * SodokuRectangle function as the background of parent StackedSquare , and will change its color when the user hover to parent node or
 * when all the squares in the same Area is filled ( have value diffirent to 0)
 * @param pane:
 */
trait updatable:
  def update():Unit
end updatable


class SodokuRectangle(  pane: SquareNode ) extends shape.Rectangle :
    private val color:Color = pane.square.color
    private val square      = pane.square
    pane.children.add(this)
    fill=color
    x = 0
    y = 0
    width =  squareLength
    height = squareLength
    var i = 0
    private def belongTofilledArea = square.filledArea().nonEmpty

    def update1() =
      val targetNode = square.filledArea().flatMap(area => pane.gridPane.sameAreaNode(area))
      if belongTofilledArea then
        targetNode.foreach(_.rect.fill.update(Gray))
        targetNode.foreach(_.rect.requestFocus())
        pane.gridPane.requestFocus()
      else
        targetNode.foreach( node => node.rect.update2(false))
        targetNode.foreach(_.rect.requestFocus())
        pane.gridPane.requestFocus()

    private def update2(control:Boolean):Unit =
      if !belongTofilledArea then
        if control then
          this.fill.update(color.darker)
        else
          this.fill.update(color)

    update1()
    parent.value.hoverProperty.onChange((_, _, _) =>
      pane.bottomBar.foreach(pane=> pane.update(square.possibleNumbers(false).contains(pane.number)))
      this.update1()
      this.update2( parent.value.hoverProperty().value)
    )

end SodokuRectangle


/**
 * @param pane:parent StackedSquare
 * @return : This function will always A Vector of 4 dotted line that repesent the boundary of the square represented parent StackedSquare.
 *           These line visiblity will be decide by the location of the square.
 *           In detail, the visibilties of these line will initially set to false ,
 *           and will be change to true if the neigbor of the square in the direction of each line is not in the sub area
 */
def createDottedLine(pane:SquareNode):Vector[Line]=
  var result:Vector[Line] = Vector()
  var square = pane.square
  var index  = square.position
  var neighboorSubArea = square.neighbor().filter(_.getSubArea.get == square.getSubArea.get).map(_.position)
  val bot = Line(0,0,squareLength,0)
  val strokeWidth =5
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

  result.foreach(_.strokeWidth = dottedLineWidth)
  result.foreach(_.getStrokeDashArray.addAll(0.01,7))
  result.foreach( pane.children.add(_))
  result.foreach(_.stroke = Brown.darker.darker)
  result


/**
 * This class repesent the value of the Square the parent StackedSquare represent ,and act as KeyBoardEvent Handler.
 * Each time the user enters the valid input. NumberBox will update its textProperty accordingly via the function update.
 * @param pane:parent StackedSquare
 * */
class NumberBox( pane:SquareNode) extends Text :
    val square = pane.square
    pane.children.add(this)
    this.scaleX = numberBoxScale
    this.scaleY = numberBoxScale
    def update():Unit=
      this.requestFocus()
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
        case _                  => ()
      this.visible = true
      pane.rect.update1()
      if Sodoku.puzzle.isWin then
        pane.gridPane.showWinMessage()
      this.update()
    }

/**
 * @param pane : Parent StackedSquare
 * @return This function will always return a Vector of 4 Line , Which act as te boundary of The parent StackedSquare.
 *         StorkeWidth of these line will be decided depend of the location of the parent StackedSquare
 */
def createPath(pane: SquareNode): Vector[Line] =
  var result: Vector[Line] = Vector()
  var square = pane.square
  var rowindex = square.getRow.map(_.position)
  var columnindex = square.getColumn.map(_.position)

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
end createPath

/**
 * Represent number of possible combination that can be put to the SubArea which the square represented by its parent Stacked Square belong to.
 * Visibilty is initialy set to false , unless the prarent StackedSquare is clicked ,and will set back to false ,when the user hover the cursor
 * away.
 * @param pane:Parent StackedSquare
 */
class PossibleComb(pane:SquareNode) extends Text:
  this.setText("")
  val numberBox =pane.numberBox
  pane.children.add(this)
  def update():Unit=
    val newValue = pane.square.numberOfPossibleComb
    this.setText(""+newValue.get)
  this.update()
  this.visible = false

  pane.hoverProperty().onChange( (_,_,_) =>
    pane.numberBox.visible = true
    this.visible = false)

  pane.numberBox.textProperty().onChange( (_,_,_) =>
    pane.gridPane.allStackedSquare.foreach( _.possibleComb.update())
    println("a") )






