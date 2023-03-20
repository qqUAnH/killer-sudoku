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


// create a StackPane that combine a square a path and a text box which represent Square's value
class StackedSquare(x:Int,y:Int,val gridPane: SodokuGrid,bottomBar:Array[BottomStackPane]) extends StackPane :
    this.focusTraversable = true
    gridPane.add(this,x,y)
    // create and add components to the pane
    val square         = Sodoku.getSquare(x+y*9)
    val rect           = SodokuRectangle(this)
    val numberBox      = NumberBox(this)
    val path           = createPath(this)
    val dot            = createDottedLine(this)
    val possibleComb   = PossibleComb(this)
    
    //ADD a small number at the left cornner of the pane that indicate of of the subArea
    if square.isFirstSquare && square.getSubArea.isDefined then
      val sumText = new Text(""+ square.getSubArea.get.sum)
      sumText.alignmentInParent = Pos.TopLeft
      sumText.fill = Red
      sumText.scaleY = sumTextScale
      sumText.scaleX = sumTextScale
      this.children.add(sumText)

    this.onMouseClicked = (m:MouseEvent) => {
      bottomBar.foreach(pane=> pane.updateColor(square.possibleNumbers.contains(pane.number)))
      bottomBar.foreach(_.requestFocus())
      this.requestFocus()
      possibleComb.visible = true
      numberBox.visible    = false
      m.consume()
    }
end StackedSquare



class BottomStackPane( index:Int) extends StackPane():
  val number     = index+1
  private val candidate = new Text(""+(number))
  candidate.scaleX = candidateScale
  candidate.scaleY = candidateScale

  // change color of candidate :Text to Read if the candidate number is according to the game rule , black otherwise )
  def updateColor(possible: Boolean): Unit =
    if possible then
      candidate.fill = White
    else
      candidate.fill = Black
  
  this.alignment = Pos.Center

  private val rectangle = new Rectangle:
    width = squareLength
    height =squareLength
    fill =Gray
  this.children.addAll(rectangle)
  this.children.add(candidate)







