package logic

import scalafx.print.PrintColor
import scalafx.scene.paint
import scalafx.scene.paint.Color



class Square(var value:Int , val position:Int , val puzzle: Puzzle) {
  var possibleNumbers  = Vector.tabulate(9)(x=>x+1)
  val validNumber      = Vector.tabulate(9)(x=>x+1)
  var row     : Option[Row] = None
  var column  : Option[Column] = None
  var box     : Option[Box] = None
  var subArea : Option[SubArea] = None

  def area : Vector[Option[Area]] = Vector(row,column,box,subArea)
  
  def isValid: Boolean =
    row.isDefined && column.isDefined && box.isDefined && subArea.isDefined && (color != Color.White)
  
  def isFirstSquare :Boolean =
    subArea.map( area => area.squares.head).forall( _ == this )

  def setValue( number : Int) =
    value = number
    row.foreach(_.squares.foreach(_.updatePossibleNumbers()))
    column.foreach(_.squares.foreach(_.updatePossibleNumbers()))
    box.foreach(_.squares.foreach(_.updatePossibleNumbers()))

  def color: Color =
    if this.subArea.isDefined then
      this.subArea.get.color.getOrElse(Color.White)
    else
      Color.White

  def addArea[B <: Area ]( area : B):Unit =
    area match
      case  a:SubArea => this.subArea = Some(a)
      case  a:Row     => this.row     = Some(a)
      case  a:Column  => this.column  = Some(a)
      case  a:Box     => this.box     = Some(a)

  def sameArea[B <: Area]( area: B):Boolean =
    area match
      case a: SubArea => this.subArea.get == area
      case a: Row     => this.row.get     == area
      case a: Column  => this.column.get  == area
      case a: Box     => this.box.get     == area

  def filledArea() :Vector[Area]=
    area.map(_.get).filter(_.isFilled())

  // this function isn't finished
  def updatePossibleNumbers() =
  // this methods throw error which mean we have read box yet
    require( row.isDefined && column.isDefined  && subArea.isDefined && box.isDefined)
    possibleNumbers= validNumber.filter(number => 
      !row.forall(_.usedDigits.contains(number))
      && !column.forall(_.usedDigits.contains(number)
      && !box.forall(_.usedDigits.contains(number))))


  def neighbor(): Vector[Square] =
    val helper = position+1
    Vector(position+1,position-1,position+9,position-9)
      .filter( index => index >= 0 && index < 81)
      .map( index => puzzle.square(index))
      .filter( square => square.row.get == this.row.get || square.column.get == this.column.get )
}
object Square {
  def apply( value:Int , position: Int ) :Square =
    new Square(value , position, Sodoku.getPuzzle)
}