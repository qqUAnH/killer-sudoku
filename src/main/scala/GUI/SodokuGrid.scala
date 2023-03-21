package GUI
import scalafx.scene.layout._
import logic._


/**
 * This Class represnt 
 * @param bottomPane:An Array of BottomStackedPane which represnt possible number can be fit to the currently hovered StackedPane according the game rule .
 */
class SodokuGrid(val bottomPane:Array[BottomStackPane]) extends GridPane {
  
  var allStackedSquare :Vector[StackedSquare]= Vector()

  /**
   * 
   * @param area:Target Area Object
   * @tparam B  :Any subClass of AreaType (could be a row ,column ,box ,and SubArea  )
   * @return    :All stackedSquareNode in the target area.
   */
  def sameAreaNode[B <: Area](  area: B ) :Vector[StackedSquare] =
    allStackedSquare.filter( _.square.sameArea( area))

  /**
   * This method is called once when the Program start , and is called whenever the user load a new puzzle 
   */
  def update() =
    this.children = List()
    val bottombar = new GridPane()
    for i <- 0 until bottomPane.length do
      bottombar.add(bottomPane(i), i, 0)

    this.columnConstraints = Array.tabulate(9)(x => ColumnConstraints(squareLength))
    this.rowConstraints = Array.tabulate(10)(x => RowConstraints(squareLength))
    this.add(bottombar, 0, 9, 9, 1)
    for {x <- 0 until 9
         y <- 0 until 9
         } do
      allStackedSquare = allStackedSquare :+ new StackedSquare(x, y, this, bottomPane)
}
