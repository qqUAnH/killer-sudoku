package logic
import scalafx.scene.paint.Color

class Square(var value:Int,val position:Int ,val puzzle: Puzzle) {
  var possibleNumbers  = Vector.tabulate(9)(x=>x+1)
  var row     : Option[Row] = None
  var column  : Option[Column] = None
  var box     : Option[Box] = None
  var subArea : Option[SubArea] = None

  def setValue( number : Int) =
    if number == 0 || this.possibleNumbers.contains(number) then
      value = number

  def color =
    if this.subArea.isDefined then
      this.subArea.get.color.get.getHue

  // this function isn't finished
  def updatePossibleNumbers() =
    require( row.isDefined && column.isDefined && box.isDefined && subArea.isDefined)
    this.possibleNumbers.filter( number => row.get.validate(number) && column.get.validate(number) && box.get.validate(number) && subArea.get.validate(number))

  def neighbor(): Vector[Square] =
    val helper = position+1
    Vector(position+1,position-1,position+9,position-9)
      .filter( index => index >= 0 && index < 81)
      .map( index => puzzle.square(index))
      .filter( square => square.row.get == this.row.get || square.column.get == this.column.get )

}
