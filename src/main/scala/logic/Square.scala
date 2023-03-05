package logic

import Game.Sodoku
import scalafx.print.PrintColor
import scalafx.scene.paint
import scalafx.scene.paint.Color



class Square(var value:Int , val position:Int , val puzzle: Puzzle) {
  var possibleNumbers  = Vector.tabulate(9)(x=>x+1)
  var row     : Option[Row] = None
  var column  : Option[Column] = None
  var box     : Option[Box] = None
  var subArea : Option[SubArea] = None

  def isFirstSquare :Boolean =
    subArea.map( area => area.squares.head).forall( _ == this )

  def setValue( number : Int) =
    if number == 0 || this.possibleNumbers.contains(number) then
      value = number

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

  // this function isn't finished
  def updatePossibleNumbers() =
    require( row.isDefined && column.isDefined && box.isDefined && subArea.isDefined)
    this.possibleNumbers.filter( number => row.get.validate() && column.get.validate() && box.get.validate() && subArea.get.validate())

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