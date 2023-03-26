package logic
import scala.collection.mutable.Buffer
import scalafx.scene.paint.Color

import scala.util.Random
import scala.util.Try

class Puzzle {
  private var squares :Vector[Square]   = Vector.tabulate(81)(x => new Square(0, x,this))
  private var columns :Vector[Column]   = Vector.tabulate(9) ( x => Column( squares( Vector.tabulate(9)(i=> i*9+  x)),x))
  private var rows    :Vector[Row]      = Vector.tabulate(9)(x => Row(squares(Vector.tabulate(9)(i => x * 9 + i)), x))
  private var boxes   :Vector[Box]      = Vector()
  private var subAreas: Buffer[SubArea] = Buffer()

  // add square to row this is a little bit trickier
  for
    rowGroup <- 0 until 3
  do
    val Rows = Vector.tabulate(3)( x => row(rowGroup*3 +x).squares.grouped(3).toVector)
    for  y <-0 until 3 do
      val boxSquare:Vector[Square] = Rows.flatMap( _.apply(y))
      println( boxSquare.length)
      boxes = boxes :+ Box( boxSquare, rowGroup*3 +y)

  def setUpPuzzle2( processedData :Vector[SubArea]) =
    try
      subAreas = Buffer()
      val squareValue = processedData.flatMap(_.squares).sortBy(_.position).map(_.value)
      this.squares.zip(squareValue).foreach(((square: Square, number: Int) => square.setValue(number)))

      columns.foreach(_.addSquares())
      rows.foreach(_.addSquares())
      boxes.foreach(_.addSquares())

      for i <- processedData do
        val newSubArea = new SubArea(  squares(i.squares.map(_.position).toVector) , i.sum)
        newSubArea.addSquares()
        this.subAreas.append(newSubArea)
      subAreas.foreach(_.updatePossibleComb())
      coloring(0)

      if !squares.forall(_.isValid) then

        throw Exception("corrupted data")
      else
        println("Sucess")
    catch
      case _ => println("corrupted data")
  end setUpPuzzle2

  private def coloring( index :Int) :Unit =
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


  def isGameRuleBroken:Boolean =
    !allrows().forall(x => x.validate())
      && !allcolumns().forall(x => x.validate())
      && !allSubAreas().forall(_.validate())

  def isWin :Boolean =
    allSquare().forall( !_.isEmpty)
      && !isGameRuleBroken

  /**
   * @todo:Currently false I should remain to fill without any probelm for a very long while but the program began to back track when the index is 16
   *
   * @param index
   * @param targetSquare
   */
  def solve(index:Int , targetSquare:Vector[Square]) :Unit  =
    if index == 0 then
      val currentSquare =targetSquare(index)
      if currentSquare.haveNonePossilbeNumber then
        println("impossible Value")
      else
        currentSquare.setValue( currentSquare.possibleNumbers(0) )
        solve(index+1,targetSquare)
    else if  index < targetSquare.length then
      val currentSquare =  targetSquare(index)
      val previousSquare =targetSquare(index-1)
      if currentSquare.haveNonePossilbeNumber then
        if index > 1 && previousSquare.haveNonePossilbeNumber then
          previousSquare.usedNumberinSodokuSolver=Buffer()
          targetSquare(index-2).usedNumberinSodokuSolver.append( targetSquare(index-2).value)
          previousSquare.setValue(0)
          targetSquare(index-2).setValue(0)
          solve(index-2,targetSquare)
        else
          previousSquare.usedNumberinSodokuSolver.append(previousSquare.value)
          previousSquare.setValue(0)
          solve(index-1,targetSquare)
      else
        currentSquare.setValue(currentSquare.possibleNumbers(0))
        solve(index+1,targetSquare)
    else
      println(isWin)
  end solve




  def emptySquare       =
    squares.filter(_.isEmpty)

  def square(index:Int) = squares(index)
  def squares( list : Vector[Int]) : Vector[Square] =
    list.map( x => square(x) )

  def row   (index:Int) = rows(index)
  def column(index:Int) = columns(index)
  def box   (index:Int) = boxes(index)

  def allrows()     :Vector[Row]      = this.rows
  def allcolumns()  :Vector[Column]   = this.columns
  def allSquare()   :Vector[Square]   = this.squares
  def allSubAreas() :Buffer[SubArea]  = this.subAreas
  def allBoxes()    = this.boxes
}
