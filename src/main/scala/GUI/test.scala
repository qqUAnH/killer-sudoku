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
import Game.Game
import javafx.animation.AnimationTimer
import javafx.event.ActionEvent
import javafx.scene.input.{KeyEvent, MouseEvent}
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
      title = "SODOKU"
      width = 500
      height = 500
      resizable = false
    /*
    Create root gui component, add it to a Scene
    and set the current window scene.
    */
    val root = GridPane()
    val scene = Scene( parent =root)


    stage.scene = scene

    def rectangle():shape.Rectangle = new shape.Rectangle:
      fill=White
      x = 0
      y = 0
      width = 50
      height = 50
      var i = 0


    def createStackPane(x:Int,y:Int) = new StackPane:
      this.focusTraversable = true
      root.add(this,x,y)

      val rect = rectangle()

      val square = sodoku.square(x+y*9)
      val numberProperty = StringProperty(""+square.value)
      val number = Text(""+square.value)
      val canvas = new Canvas(50,50)
      number.textProperty().bind(numberProperty)


      rect.fill = if this.square.subArea.isDefined then this.square.subArea.get.color.get else White


      def stackchildren = this.children


      var i = 0
      val path = new Path
      path.elements += MoveTo(0,0)
      path.elements += VLineTo(50)
      path.elements += HLineTo(50)
      path.elements += VLineTo(0)
      path.elements += HLineTo(0)
      path.elements += new ClosePath()

      this.onKeyPressed = (ke:KeyEvent) =>{
        numberProperty.set(""+2)
        println("A")
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
        println( "aaa" +"")
        this.requestFocus()
      }

      this.children.addAll(rect,path,number)


    def createColumnConstraints(): ColumnConstraints =
      new ColumnConstraints :
        percentWidth = 10
    def createRowConstraints() :RowConstraints =
      new RowConstraints :
        percentHeight = 10

    for {x <- 0 until 9
         y <- 0 until 9
         } do
      createStackPane(x,y)

    root.columnConstraints = Array.tabulate(9)(x=> createColumnConstraints())
    root.rowConstraints = Array.tabulate(9)(x => createRowConstraints())
    val timer =  scalafx.animation.AnimationTimer( t=>{

    })
    timer.start()

