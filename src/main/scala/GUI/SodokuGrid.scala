package GUI
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.{Alert, ButtonType}
import scalafx.scene.layout.*
import logic.*


/**
 * This Class represnt 
 * @param bottomPane:An Array of BottomStackedPane which represnt possible number can be fit to the currently hovered StackedPane according the game rule .
 */
class SodokuGrid(val bottomPane:Array[PossibleNumberNode]) extends GridPane {
  
  var allStackedSquare :Vector[SquareNode]= Vector()
  val possibleComb = new PossibleComb2

  /**
   * 
   * @param area:Target Area Object
   * @tparam B  :Any subClass of Area Trait (could be a row ,column ,box ,and SubArea  )
   * @return    :All stackedSquareNode in the target area.
   */
  def sameAreaNode[B <: Area](  area: B ) :Vector[SquareNode] =
    allStackedSquare.filter( _.square.belongToArea( area))

  /**
   * This method is called once when the Program start , and is called whenever the user load a new puzzle 
   */


  def showWinMessage() =
    val winMsg = new Alert(AlertType.INFORMATION)
    winMsg.setTitle("MSG")
    winMsg.setContentText("YOU WON-YAY!!!")
    winMsg.showAndWait.ifPresent( (rs: ButtonType) =>  if (rs eq ButtonType.OK) System.out.println("Pressed OK."))

  def update() =
    println("a")
    this.children = List()
    val bottombar = new GridPane()
    for i <- 0 until bottomPane.length do
      bottombar.add(bottomPane(i), i, 0)
    this.columnConstraints = Array.tabulate(9)(x => ColumnConstraints(squareLength))
    this.rowConstraints = Array.tabulate(11)(x => RowConstraints(squareLength))
    this.add(bottombar, 0, 9, 9, 1)
    for {x <- 0 until 9
         y <- 0 until 9
         } do
      allStackedSquare = allStackedSquare :+ new SquareNode(x, y, this, bottomPane)

    this.add( possibleComb ,0 ,10)
}

