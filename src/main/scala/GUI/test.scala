package GUI
import javafx.scene.shape.Rectangle
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3
import scalafx.scene.control.CheckBox
import scalafx.scene.{Scene, shape}
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.Rectangle
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
import Game.Game


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
      width = 1000
      height = 1000
    /*
    Create root gui component, add it to a Scene
    and set the current window scene.
    */
    val root = GridPane()
    val scene = Scene( parent =root)
    stage.scene =scene


    // should I use some
    def rectangle():shape.Rectangle = new shape.Rectangle:
      x = 100
      y = 100
      width = 50
      height = 50
      fill = Gray

    def createStackPane(squareposition: Int) = new StackPane:
      val rect = rectangle()
      val square = sodoku.square(squareposition)
      val number = new Text(""+square.value)
      this.children.addAll(rect,number)





    for {x <- 0 until 9
         y <- 0 until 9
         } do
      root.add(createStackPane(x+y*9),x,y)

    def createColumnConstraints(): ColumnConstraints =
      new ColumnConstraints :
        percentWidth = 10
    def createRowConstraints() :RowConstraints =
      new RowConstraints :
        percentHeight = 10

    root.columnConstraints = Array.tabulate(9)(x=> createColumnConstraints())
    root.rowConstraints = Array.tabulate(9)(x => createRowConstraints())