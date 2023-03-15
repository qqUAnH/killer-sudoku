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
import logic.*
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
    val root      = new VBox()
    val bottombar = new GridPane()
    val menuBar   = new GameMenu()

    val bottomPanes = Array.tabulate(9)( x =>new BottomStackPane(x))
    for i <- 0 until bottomPanes.length do
      bottombar.add(bottomPanes(i),i,0)

    val grid      = new GridPane:
      this.columnConstraints = Array.tabulate(9)(x=> ColumnConstraints(squareLength))
      this.rowConstraints = Array.tabulate(10)(x => RowConstraints(squareLength))
      this.add(bottombar,0,9,9,1)

      for {x <- 0 until 9
         y <- 0 until 9
         } do
      new StackedSquare(x,y,this,bottomPanes)

    val scene=Scene(parent= root)

    root.children.addAll(menuBar,grid)

    root.setAlignment(Pos.TopLeft)

    stage.scene = scene

end Main
