package logic

import scalafx.print.PrintColor
import scalafx.scene.paint
import scalafx.scene.paint.Color
import scala.collection.mutable.Buffer

sealed class Square(var value:Int , val position:Int , val puzzle: Puzzle) {
  private var possibleNumbers  = Vector.tabulate(9)(x=>x+1)
  private val validNumber: Vector[Int] = Vector.tabulate(9)(x => x + 1)
  private var row     : Option[Row]     = None
  private var column  : Option[Column]  = None
  private var box     : Option[Box]     = None
  private var subArea : Option[SubArea] = None
  //methods is used to help us take the data out only
  def possibleNumbers(takeSubAreaIntoConsideration :Boolean):Vector[Int]=
    if takeSubAreaIntoConsideration then
      possibleNumbers.filter(_+subArea.get.currentSum <= subArea.get.sum)
    else possibleNumbers

  def getRow      = this.row
  def getColumn   = this.column
  def getBox      = this.box
  def getSubArea  = this.subArea
  def area : Vector[Option[Area]] = Vector(row,column,box,subArea)


  //called when the we try to load new puzzle ,this methods should change the row, column, box, SUbArea to others value than None
  def addArea[B <: Area](area: B): Unit =
    area match
      case a: SubArea => this.subArea = Some(a)
      case a: Row => this.row = Some(a)
      case a: Column => this.column = Some(a)
      case a: Box => this.box = Some(a)

  //Will be used in GUI to connect related Node.
  def belongToArea[B <: Area](area: B): Boolean =
    area match
      case a: SubArea => this.subArea.get == area
      case a: Row => this.row.get         == area
      case a: Column => this.column.get   == area
      case a: Box => this.box.get         == area

  // Any square should belong to a row ,column, box , and subArea , return false if respective varible is undefined
  def isValid       :Boolean = row.isDefined && column.isDefined && box.isDefined && subArea.isDefined && (color != Color.White)

  def haveNonePossilbeNumber:Boolean = this.possibleNumbers.isEmpty

  def isFirstSquare :Boolean = subArea.map( area => area.squares.head).forall( _ == this )

  //reutrn an Vector of Area which is filed this square belong to
  def filledArea() :Vector[Area]= area.map(_.get).filter(_.isFilled)

  def isEmpty       :Boolean = this.value == 0
  //change value of a square then update posssible squares in the same row , column and box
  //Could this be improved
  def setValue(number: Int): Unit=
    value = number
    row.foreach(_.squares.foreach(_.updatePossibleNumbers()))
    column.foreach(_.squares.foreach(_.updatePossibleNumbers()))
    box.foreach(_.squares.foreach(_.updatePossibleNumbers()))
    subArea.foreach(_.updatePossibleComb())

  def numberOfPossibleComb:Int =
    subArea.foreach(_.updatePossibleComb())
    subArea.map(_.possibleComb).get

  def color: Color =
    if this.subArea.isDefined then
      this.subArea.get.color.getOrElse(Color.White)
    else
      Color.White


  /**
   * @TODO:Need change in validate function
   */
  def updatePossibleNumbers( ) =
    require( row.isDefined && column.isDefined  && subArea.isDefined && box.isDefined)
    possibleNumbers= validNumber.filter(number =>
         !row   .forall(_.usedDigits.contains(number))
      && !column.forall(_.usedDigits.contains(number))
      && !box   .forall(_.usedDigits.contains(number))
    )

  def neighbor(): Vector[Square] =
    val helper = position+1
    Vector(position+1,position-1,position+9,position-9)
      .filter( index => index >= 0 && index < 81)
      .map( index => puzzle.square(index))
      .filter( square => square.row.get == this.row.get || square.column.get == this.column.get )

  override def toString: String = "" + this.value
}
//a companion object to create new instances of Square : Will be a great aid to circe Json decoder and encoder
object Square {
  def apply( value:Int , position: Int ) :Square =
    new Square(value , position, Sodoku.puzzle)
}