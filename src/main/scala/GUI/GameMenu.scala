package GUI
import scalafx.Includes._
import scalafx.scene.control.MenuBar
import scalafx.scene.control._
import scalafx.event._
import scalafx.util

class GameMenu() extends MenuBar:
  val fileMenu =  Menu("File")
  val saveItem =  MenuItem("Save")
  val exitItem =  MenuItem("Exit")
  val loadItem =  RadioMenuItem("Load")

  

  exitItem.onAction = (e:ActionEvent) => {sys.exit(0)}



  fileMenu.items =List( loadItem ,saveItem,new SeparatorMenuItem ,exitItem)
  this.menus = List(fileMenu)

end GameMenu


