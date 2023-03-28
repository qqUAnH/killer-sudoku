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


/**
 * This class act as a communication ground for the GUI and file management scheme.
 * @todo : Make sure that the the file will throw expection when the user try to save onto the program code
 * @param grid : The parent of all StackedSquare .If each StackedSquare represent a square in the puzzle,and SodokuGrid represnt the whole puzzle.
 * @param stage: Parent Stage
 */
class Menu_Controller(grid:SodokuGrid ,stage:JFXApp3.PrimaryStage) extends MenuBar:
  private val fileMenu =  Menu("File")
  private val saveItem =  MenuItem("Save")
  private val exitItem =  MenuItem("Exit")
  private val loadItem =  Menu("Load")
  private val solveItem= Menu("Solve")

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
  solveItem.onAction = (e:ActionEvent) => {
    Sodoku.getPuzzle.solve(0 , Sodoku.getPuzzle.emptySquare)
    grid.update()
    grid.requestFocus()
  }
  
  exitItem.onAction = (e:ActionEvent) => {sys.exit(0)}
  fileMenu.items =List( loadItem ,saveItem,new SeparatorMenuItem ,exitItem)
  this.menus = List(fileMenu,solveItem)

end Menu_Controller


