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

class Menu_Controller(grid:SodokuGrid) extends MenuBar:
  private val fileMenu =  Menu("File")
  private val saveItem =  MenuItem("Save")
  private val exitItem =  MenuItem("Exit")
  private val loadItem =  Menu("Load")

  val allSavefile = JSON.list()
  for file <- allSavefile do
    val newRadio =RadioMenuItem(file)
    newRadio.onAction = (e:ActionEvent) => {
      Sodoku.load(file)
      grid.children.foreach(_.requestFocus())
      grid.update()
    }


    loadItem.items.add(newRadio)


  exitItem.onAction = (e:ActionEvent) => {sys.exit(0)}
  fileMenu.items =List( loadItem ,saveItem,new SeparatorMenuItem ,exitItem)
  this.menus = List(fileMenu)

end Menu_Controller


