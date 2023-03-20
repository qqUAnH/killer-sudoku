package GUI
import scalafx.embed.swing.SwingNode.*
import scalafx.application.JFXApp3
import scalafx.scene.{Scene, shape}
import scalafx.Includes.*
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.VBox
import scalafx.scene.layout.HBox
import scalafx.scene.layout.GridPane
import scalafx.geometry.Pos
import logic.*

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
    val bottomPanes = Array.tabulate(9)( x =>new BottomStackPane(x))
    val grid  =  SodokuGrid(bottomPanes)
    grid.update()
    val menuBar =  Menu_Controller(grid,stage)

    val scene=Scene(parent= root)

    root.children.addAll(menuBar,grid)

    root.setAlignment(Pos.TopLeft)

    stage.scene = scene

end Main
