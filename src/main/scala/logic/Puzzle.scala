package logic
import scala.collection.mutable.Buffer
import scalafx.scene.paint.Color
import scala.util.Random
import scala.util.Try

class Puzzle {
  private val squares = Vector.tabulate(81)( x => Square(0,x,this))
  private val columns = Vector.tabulate(9) ( x => Column(squares( Vector.tabulate(9)(i=> i*9+  x))        ,x))
  private val rows    = Vector.tabulate(9) ( x => Row   (squares( Vector.tabulate(9)(i=> x*9 + i))        ,x))
  private val boxes   = Vector.tabulate(9) ( x => Box   (squares( Vector.tabulate(9)(i =>x*3 + (i/3)*9+1)),x))
  private val subAreas : Buffer[SubArea] = Buffer()

  def setUpPuzzle(squareValue:Array[Int] , listOfSubArea:Buffer[Vector[Int]] ) =
    // Setup columns ,box and rows
    for rawData <- listOfSubArea do
      /// COUld have an error here
      val newSubArea = new SubArea(squares(rawData.drop(1).map(_-1))  ,rawData.apply(0))
      this.subAreas.append(newSubArea)

    columns .foreach(_.addSquares())
    rows    .foreach(_.addSquares())
    subAreas.foreach(_.addSquares())
    boxes   .foreach(_.addSquares())
    coloring(0)

    this.squares.zip(squareValue).foreach( (square :Square,number:Int) => square.setValue(number))

  def coloring( index :Int) :Unit =
    // can throw error here
    if index == 0 then
      subAreas(index).newColor()
      subAreas.drop(1).foreach(area => area.updatePossibleColor())
      coloring(index+1)
    else if index < subAreas.length then
      val area         = subAreas(index)
      val previousArea = subAreas(index-1)
      if area.possibleColor.isEmpty then
        previousArea.possibleColor = previousArea.possibleColor.filter(_ != previousArea.color.get)
        coloring(index-1)
      else
        area.newColor()
        subAreas.drop(index+1).foreach( area=> area.updatePossibleColor())
        coloring(index+1)



  def isWin() :Boolean =
    allSquare().map( x => x.value ).count( _ >0)  == 81
      && allrows()   .forall( x=> x.validate())
      && allcolumns().forall( x=> x.validate())

  def square(index:Int) = squares(index)

  def squares( list : Vector[Int]) : Vector[Square] =
    list.map( x => square(x) )

  def row   (index:Int) = rows(index)

  def column(index:Int) = columns(index)

  def box   (index:Int) = boxes(index)

  def allrows(): Vector[Row] = this.rows

  def allcolumns() :Vector[Column] = this.columns

  def allSquare() :Vector[Square] = this.squares

  def allSubAreas() :Buffer[SubArea] = this.subAreas
}
