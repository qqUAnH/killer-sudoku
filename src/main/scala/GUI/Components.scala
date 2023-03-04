package GUI

import Game.Sodoku
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import logic.Puzzle
import scalafx.scene.paint.Color
import scalafx.beans.property.StringProperty
import scalafx.scene.layout.{ColumnConstraints, GridPane, RowConstraints, StackPane}
import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.{Parent, shape}
import scalafx.scene.shape.{HLineTo, MoveTo, Path, Rectangle, VLineTo}
import scalafx.scene.text.Text
import scalafx.geometry.Pos
import scalafx.Includes.*



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
        fill.update(color.darker())
      else
        i=0
        fill.update(color))
end CreateRectangle



def createPath() = new Path:
  this.elements.addAll(MoveTo(0,0),VLineTo(squareLength),HLineTo(squareLength),VLineTo(0),HLineTo(0))
end createPath


def createColumnConstraints(): ColumnConstraints =
  new ColumnConstraints :
    percentWidth = comlumnPercentage
end createColumnConstraints


def createRowConstraints() :RowConstraints =
  new RowConstraints :
    percentHeight = rowPercentage
end createRowConstraints


def createStackPane(x:Int,y:Int, gridPane: GridPane) =
  new StackPane:
    this.focusTraversable = true
    gridPane.add(this,x,y)
    // create and add components to the pane
    val square         = Sodoku.getSquare(x+y*9)
    val rect           = CreateRectangle(square.color,this)
    val path           = createPath()
    val numberProperty = StringProperty("")
    val number         = Text("")
    number.textProperty().bind(numberProperty)
    this.children.addAll(path,number)

    if square.isFirstSquare && square.subArea.isDefined then
      val sumText = new Text(""+ square.subArea.get.sum)
      sumText.alignmentInParent = Pos.TopLeft
      this.children.add(sumText)
    this.onKeyPressed = (ke:KeyEvent) =>{
    var helper = 10
    ke.getCode match
      case KeyCode.DIGIT1     => helper = 1
      case KeyCode.DIGIT2     => helper = 2
      case KeyCode.DIGIT3     => helper = 3
      case KeyCode.DIGIT4     => helper = 4
      case KeyCode.DIGIT5     => helper = 5
      case KeyCode.DIGIT6     => helper = 6
      case KeyCode.DIGIT7     => helper = 7
      case KeyCode.DIGIT8     => helper = 8
      case KeyCode.DIGIT9     => helper = 9
      case KeyCode.BACK_SPACE => helper = 0
      case _              => helper = 10

      if helper == 0 then
        square.setValue(0)
        numberProperty.set("")
      else if helper < 10 then
        square.setValue(helper)
        numberProperty.set(""+helper) }
    this.onMouseClicked = (m:MouseEvent) => {
      this.requestFocus()
      m.consume()
    }

end createStackPane