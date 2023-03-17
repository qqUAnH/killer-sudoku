package GUI
import scalafx.scene.layout._

class SodokuGrid(val bottomPane:Array[BottomStackPane]) extends GridPane {
  
  var allStackedSquare :Vector[StackedSquare]= Vector()
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
