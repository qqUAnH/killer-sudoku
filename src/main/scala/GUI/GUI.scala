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
import javafx.animation.AnimationTimer
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import logic.{Puzzle, Sodoku, Square, SubArea}
import scalafx.scene.layout.GridPane.{getColumnIndex, getRowIndex}
import scalafx.scene.paint.Color
import scalafx.application.Platform
import scalafx.animation.AnimationTimer

import scala.language.postfixOps


object Main extends JFXApp3:
  //SHould some be used in this situation


  val sodoku=Sodoku.getPuzzle
  def start(): Unit =
    //NOte for references LOL
    //https://stackoverflow.com/questions/46997267/how-do-i-insert-text-into-a-shape-in-javafx
    //Create primary stage
    stage = new JFXApp3.PrimaryStage:
      title     = "KILLER-SODOKU"
      width     = stageWidth
      height    = stageHeight
      resizable = false

    val root       = StackPane()
    val grid       = new GridPane()
    val secondgrid = new GridPane()
    val scene = Scene( parent =root)
    root.children.add(grid)
    root.setAlignment(Pos.TopLeft)
    stage.scene = scene

    grid.columnConstraints = Array.tabulate(9)(x=> createColumnConstraints())
    grid.rowConstraints = Array.tabulate(9)(x => createRowConstraints())

    for {x <- 0 until 9
         y <- 0 until 9
         } do
      new StackedSquare(x,y,grid)
end Main
