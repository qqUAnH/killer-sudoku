package logic

class Puzzle {
  private val squares = Vector.tabulate(81)( x => Square(0,x)   )
  private val columns = Vector.tabulate(9) ( x => Column(this,x))
  private val rows    = Vector.tabulate(9) ( x => Row   (this,x))
  private val boxes   = Vector.tabulate(9) ( x => Box   (this,x))

  def setUpPuzzle(x:Array[Int]) =
    for i <- 0 to 8 do
      columns.foreach(column => column.addSquare( squares(i*9+ column.position)))
      rows   .foreach(row    => row   .addSquare( squares(row.position*9 + i  )))
      // ignore boxes for now
      boxes  .foreach(box    => box   .addSquare( squares(box.position*3 + (i/3)*9+1)))
    this.squares.zip(x).foreach( (square :Square,number:Int) => square.setValue(number))

  def square(index:Int) = squares(index)

  def row   (index:Int) = rows(index)

  def column(index:Int) = columns(index)

  def box   (index:Int) = boxes(index)

  def allrows(): Vector[Row] = this.rows

  def allcolumns() :Vector[Column] = this.columns


}
