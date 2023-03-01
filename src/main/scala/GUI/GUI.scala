package GUI
import scalafx.embed.swing.SwingNode.*
import scalafx.Includes.*
import scalafx.beans.property.DoubleProperty
import scalafx.scene.shape.Rectangle
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3
import scalafx.scene.control.CheckBox
import scalafx.scene.{Scene, shape}
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color.*
import scalafx.scene.paint.Paint.*
import scalafx.scene.shape.*
import scalafx.scene.control.Label
import scalafx.scene.text.*
import scalafx.scene.canvas.Canvas
import scalafx.Includes.*
import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout.VBox
import scalafx.scene.layout.HBox
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.Background
import scalafx.scene.layout.BackgroundFill
import scalafx.scene.layout.CornerRadii
import scalafx.scene.layout.ColumnConstraints
import scalafx.scene.layout.RowConstraints
import scalafx.scene.paint.Color.*
import scalafx.scene.layout.*
import scalafx.beans.property.*
import scalafx.geometry.Pos
import Game.Game
import javafx.animation.AnimationTimer
import javafx.event.ActionEvent
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import logic.{Puzzle, Square, SubArea}
import scalafx.scene.layout.GridPane.{getColumnIndex, getRowIndex}
import scalafx.scene.paint.Color
import scalafx.application.Platform
import scalafx.animation.AnimationTimer

import scala.language.postfixOps


object Main extends JFXApp3:
  //SHould some be used in this situation
  val sodoku=Game.puzzle
  def start(): Unit =


    //NOte for references LOL
    //https://stackoverflow.com/questions/46997267/how-do-i-insert-text-into-a-shape-in-javafx
    /*
    Creation of a new primary stage (Application window).
    We can use Scala's anonymous subclass syntax to get quite
    readable code.
    */
    stage = new JFXApp3.PrimaryStage:
      title     = "KILLER-SODOKU"
      width     = stageWidth
      height    = stageHeight
      resizable = false
    /*
    Create root gui component, add it to a Scene
    and set the current window scene.
    */
    val root       = StackPane()
    val grid       = new GridPane()
    val secondgrid = new GridPane()
    val scene = Scene( parent =root)
    root.children.add(grid)
    root.setAlignment(Pos.TopLeft)


    stage.scene = scene

    def rectangle():shape.Rectangle = new shape.Rectangle:
      fill=White
      x = 0
      y = 0
      width = squareLength
      height = squareLength


    def createPath() = new Path:
       this.elements.addAll(MoveTo(0,0),VLineTo(squareLength),HLineTo(squareLength),VLineTo(0),HLineTo(0))
    end createPath

    def createInterPath(subArea: SubArea) = new Polyline:
      subArea.squares.map( square => Vector.tabulate(4)( x =>1))
      val x: Vector[Vector[(Int,Int)]] = Vector()




    end createInterPath


    def createStackPane(x:Int,y:Int) = new StackPane:

      this.focusTraversable = true
      grid.add(this,x,y)

      //set up component in the pane
      val rect           = rectangle()
      val square         = sodoku.square(x+y*9)
      val numberProperty = StringProperty("")
      val number         = Text("")
      val path           = createPath()
      number.textProperty().bind(numberProperty)
      rect.fill = if this.square.subArea.isDefined then this.square.subArea.get.color.get else White

      //helper function and varible
      def stackchildren = this.children
      var i = 0


      this.onKeyPressed = (ke:KeyEvent) =>{
        var helper = 10
        ke.getCode match
          case KeyCode.DIGIT1 => helper = 1
          case KeyCode.DIGIT2 => helper = 2
          case KeyCode.DIGIT3 => helper = 3
          case KeyCode.DIGIT4 => helper = 4
          case KeyCode.DIGIT5 => helper = 5
          case KeyCode.DIGIT6 => helper = 6
          case KeyCode.DIGIT7 => helper = 7
          case KeyCode.DIGIT8 => helper = 8
          case KeyCode.DIGIT9 => helper = 9
          case KeyCode.DIGIT0 => helper = 0

        //THis will be change later when we do command part
        if helper == 0 then
          square.setValue(0)
          numberProperty.set("")
        else if helper < 10 then
          square.setValue(helper)
          numberProperty.set(""+helper)
      }
      hover.onChange (
        (_,_,_) =>
          if i == 0 then
            rect.fill.update(Gray)
            i = 1
          else
            //THis neeed to change

            rect.fill.update(square.subArea.get.color.getOrElse(White))
            i = 0)
      this.onMouseClicked = (e:MouseEvent) => {
        this.requestFocus()
      }

      this.children.addAll(rect,path,number)
    end createStackPane



    def createColumnConstraints(): ColumnConstraints =
      new ColumnConstraints :
        percentWidth = comlumnPercentage
    def createRowConstraints() :RowConstraints =
      new RowConstraints :
        percentHeight = rowPercentage



    grid.columnConstraints = Array.tabulate(9)(x=> createColumnConstraints())
    grid.rowConstraints = Array.tabulate(9)(x => createRowConstraints())

    for {x <- 0 until 9
         y <- 0 until 9
         } do
      createStackPane(x,y)

    secondgrid.columnConstraints = Array.tabulate(9)(x=> createColumnConstraints())
    secondgrid.rowConstraints = Array.tabulate(9)(x => createRowConstraints())

