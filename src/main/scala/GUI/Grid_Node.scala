package GUI
import javafx.scene.input.{KeyCode, KeyCodeCombination, KeyCombination, KeyEvent, MouseEvent}
import jdk.jfr.Label
import logic.{Puzzle, Sodoku, Square}
import scalafx.scene.paint.Color
import scalafx.beans.property.*
import scalafx.scene.layout.{ColumnConstraints, GridPane, RowConstraints, StackPane}
import scalafx.scene.paint.Color.{Black, Blue, Brown, Gray, Red, Wheat, White, Yellow}
import scalafx.scene.{Parent, shape}
import scalafx.scene.shape.{ClosePath, HLineTo, LineTo, MoveTo, Path, Rectangle, VLineTo}
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.Includes.*
import scalafx.scene.shape.Line
import logic.Sodoku
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType


/**Each class StackedSquare represent a Square in sodoku grid.
 *
 * @param row : row index in parent SodokuGrid
 * @param column : columns index in parent SodokuGrid
 * @param gridPane : Parent SodokuGrod
 * @param bottomBar : an Array of BottomStackPane which together represent possible number that can fit into this square according to te game rule
 */

class SquareNode(row:Int, column:Int, val gridPane: SodokuGrid,val bottomBar:Array[PossibleNumberNode]) extends StackPane :
    this.focusTraversable = true
    gridPane.add(this,column,row)
    // create and add components to the pane
    val square         = Sodoku.getSquare(column+row*9)
    val rect           = SodokuRectangle(this)
    val numberBox      = NumberBox(this)
    val path           = createPath(this)
    val dot            = createDottedLine(this)
    val possibleComb   = PossibleComb(this)

    //ADD a small number at the left cornner of the pane that indicate of of the subArea
    if square.isFirstSquare && square.getSubArea.isDefined then
      val sumText = new Text(""+square.getSubArea.get.sum)
      sumText.alignmentInParent=Pos.TopLeft
      sumText.translateX = (dottedLineWidth)
      sumText.translateY = (dottedLineWidth)
      sumText.fill = Black
      this.children.add(sumText)


    this.onMouseClicked = (m:MouseEvent) => {
      bottomBar.foreach(_.requestFocus())
      this.requestFocus()
      possibleComb.visible = true
      numberBox.visible    = false
      m.consume()
    }
    this.hoverProperty().onChange( (_,_,_)
      => gridPane.possibleComb.update(this))
      
end SquareNode

/**
 * Each BottomStackedPane represnt a digit number from 1-9.The Color of this number will turn White if it is possible to put this number
 * onto the currently hovered StackedSquare , and Black otherwise.
 * Thus 9 instance of this class will enough to represnt all the possible numbers can be put onto a given StackedSquare.
 * @param index: Determine the location of the Pane in the SodokuGrid as well as the digit number this class repesent which is equal to index +1
 */
class PossibleNumberNode(index:Int) extends StackPane():
  val number     = index+1
  private val candidate = new Text(""+(number))
  candidate.scaleX = candidateScale
  candidate.scaleY = candidateScale

  // change color of candidate :Text to Read if the candidate number is according to the game rule , black otherwise )
  def update(possible: Boolean): Unit =
    if possible then
      candidate.fill = White
    else
      candidate.fill = Black
  this.alignment = Pos.Center
  val rect = new Rectangle:
    width = squareLength
    height =squareLength
    fill =Gray

  this.children.addAll(rect)
  this.children.add(candidate)

class PossibleComb2 extends Text:
  def update(pane:SquareNode) =
    this.setText(pane.square.possibleComb)





