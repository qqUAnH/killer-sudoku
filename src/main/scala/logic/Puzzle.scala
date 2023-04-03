package logic
import scala.collection.mutable.Buffer
import scalafx.scene.paint.Color

import scala.util.Random
import scala.util.Try
import scala.annotation.tailrec

class Puzzle {
  private var squares :Vector[Square]   = Vector.tabulate(81)(x => new Square(0, x,this))
  private var columns :Vector[Column]   = Vector.tabulate(9) ( x => Column( squares( Vector.tabulate(9)(i=> i*9+  x)),x))
  private var rows    :Vector[Row]      = Vector.tabulate(9)(x => Row(squares(Vector.tabulate(9)(i => x * 9 + i)), x))
  private var boxes   :Vector[Box]      = Vector()
  private var subAreas:Buffer[SubArea] = Buffer()

  // add square to row this is a little bit trickier
  for
    rowGroup <- 0 until 3
  do
    val Rows = Vector.tabulate(3)( x => rows(rowGroup*3 +x).squares.grouped(3).toVector)
    for  y <-0 until 3 do
      val boxSquare:Vector[Square] = Rows.flatMap( _.apply(y))
      println( boxSquare.length)
      boxes = boxes :+ Box( boxSquare, rowGroup*3 +y)

  def setUpPuzzle( processedData :Vector[SubArea]):Unit =
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
      this.coloring()

      if !squares.forall(_.isValid) then
        throw Exception("corrupted data")
      else
        println("Sucess")
    catch
      case _ => println("corrupted data")
  end setUpPuzzle

  // should habe  inner function
  private def coloring() :Unit =
    def inner(index:Int) :Unit =
      if index == 0 then
        subAreas(index).newColor()
        subAreas.drop(1).foreach(area => area.updatePossibleColor())
        inner(index+1)
      else if index < subAreas.length then
        val area         = subAreas(index)
        val previousArea = subAreas(index-1)
        if area.possibleColor.isEmpty then
          previousArea.possibleColor = previousArea.possibleColor.filter(_ != previousArea.color.get)
          inner(index-1)
        else
          area.newColor()
          subAreas.drop(index+1).foreach( area=> area.updatePossibleColor())
          inner(index+1)
    end inner
    inner(0)
  end coloring


  def isGameRuleBroken:Boolean =
    val re = !(getRows.forall(x => x.validate())
      && getColumns.forall(x => x.validate())
      && getBoxes.forall(x=>x.validate())
      && getSubAreas.forall(_.validate())
    )
    re

  def isWin :Boolean =
    getSquares.forall( !_.isEmpty)
      && !isGameRuleBroken

  // work for normal sodoku
  // my save file could b the problem
  // this is quite slow since we have to ry until 1

  def solve() :Option[Vector[Square]]  =
    val currentSquare = greedy
    if isGameRuleBroken then None
    else if greedy.isEmpty then
      println("Sucess")
      Some(this.getSquares)
    else if greedy.nonEmpty then
      val candidate:Vector[Int] = currentSquare.get.possibleNumbers
      var result:Option[Vector[Square]] = None
      candidate.find( x=> {
        currentSquare.foreach(_.setValue(x))
        val re = solve()
        if re.nonEmpty then
          result = re
          true
        else
         currentSquare.get.setValue(0)
         false
        })
        result
    else None
  end solve

  def greedy:Option[Square]=
    if emptySquares.nonEmpty then
      Some(emptySquares.minBy( _.possibleNumber2.length))
    else None
  def emptySquares :Vector[Square]   =
    squares.filter(_.isEmpty)

  def square(  location:Int) = squares(location)
  def squares( listOfLocation : Vector[Int]) : Vector[Square] =
    listOfLocation.map( x => square(x) )



  def getRows     :Vector[Row]      = this.rows
  def getColumns  :Vector[Column]   = this.columns
  def getSquares  :Vector[Square]   = this.squares
  def getSubAreas :Buffer[SubArea]  = this.subAreas
  def getBoxes    :Vector[Box]      = this.boxes
}
