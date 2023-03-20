package GUI
import scalafx.Includes._
import scalafx.scene.control.MenuBar
import scalafx.scene.control._
import scalafx.scene.layout.VBox
import scalafx.event._
import scalafx.util
import IO.JSON
import logic.Sodoku
import scalafx.application.JFXApp3
import scalafx.stage.FileChooser
import os.Path

class Menu_Controller(grid:SodokuGrid ,stage:JFXApp3.PrimaryStage) extends MenuBar:
  private val fileMenu =  Menu("File")
  private val saveItem =  MenuItem("Save")
  private val exitItem =  MenuItem("Exit")
  private val loadItem =  Menu("Load")


  loadItem.onAction = (e:ActionEvent) => {
    val fileChooser = FileChooser()
    val selectedFile = fileChooser.showOpenDialog(stage)
    try
      val path  = os.Path(selectedFile.toPath)
      Sodoku.load(path)
      grid.update()
    catch
      case e:NullPointerException => println("No file chossen")
  }

  saveItem.onAction = (e:ActionEvent) =>{
    try
      val fileChooser = FileChooser()
      val selectedFile = fileChooser.showOpenDialog(stage)
      val path = os.Path(selectedFile.toPath)
      Sodoku.save(path)
    catch
      case e:NullPointerException => println("No file chossen")
  }
  exitItem.onAction = (e:ActionEvent) => {sys.exit(0)}
  fileMenu.items =List( loadItem ,saveItem,new SeparatorMenuItem ,exitItem)
  this.menus = List(fileMenu)

end Menu_Controller


