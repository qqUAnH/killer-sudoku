package logic
import scala.collection.mutable.Buffer
import scalafx.scene.paint.Color

class Puzzle {
  private val squares = Vector.tabulate(81)( x => Square(0,x,this))
  private val columns = Vector.tabulate(9) ( x => Column(this,x))
  private val rows    = Vector.tabulate(9) ( x => Row   (this,x))
  private val boxes   = Vector.tabulate(9) ( x => Box   (this,x))
  private val subAreas : Buffer[SubArea] = Buffer()

  def setUpPuzzle(squareValue:Array[Int] , listOfSubArea:Buffer[Vector[Int]] ) =
    // Setup columns ,box and rows
    for i <- 0 to 8 do
      columns.foreach(column => column.addSquare( squares(i*9+ column.position)))
      rows   .foreach(row    => row   .addSquare( squares(row.position*9 + i  )))
      // ignore boxes for now
      boxes  .foreach(box    => box   .addSquare( squares(box.position*3 + (i/3)*9+1)))

    for rawData <- listOfSubArea do
      /// COUld have an error here
      val newSubArea = new SubArea(this,rawData.apply(0))
      this.subAreas.append(newSubArea)
      rawData.drop(1).foreach( squareindex => newSubArea.addSquare(squares(squareindex-1)))

    for i <- subAreas.indices do
     coloring(i)

    this.squares.zip(squareValue).foreach( (square :Square,number:Int) => square.setValue(number))

  def coloring( index :Int) :Unit =
    // can throw error here
    if index == 0 then
      subAreas(index).setColor( subAreas(index).possibleColor(0))
      subAreas.drop(1).foreach(area => area.updatePossibleColor())
    else if index < subAreas.length then
      val area         = subAreas(index)
      val previousArea = subAreas(index-1)
      if area.possibleColor.isEmpty then
        // will this line need ? since all we already updated last time
        previousArea.possibleColor =previousArea.possibleColor.filter(_ != previousArea.color.get)
        previousArea.setColor(Color.White)
        coloring(index-1)
      else
        area.setColor( area.possibleColor(0))
        subAreas.drop(index+1).foreach( area=> area.updatePossibleColor())

  def square(index:Int) = squares(index)

  def row   (index:Int) = rows(index)

  def column(index:Int) = columns(index)

  def box   (index:Int) = boxes(index)

  def allrows(): Vector[Row] = this.rows

  def allcolumns() :Vector[Column] = this.columns

  def allSquare() :Vector[Square] = this.squares

  def allSubAreas() :Buffer[SubArea] = this.subAreas
}
