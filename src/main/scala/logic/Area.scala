package logic


import scala.collection.mutable.Buffer
import scala.collection.Iterator
import scalafx.scene.paint.Color

trait Iterator[Square]:
  def hasNext:Boolean

  def next():Square
end Iterator

trait Area:
  val squares:Buffer[Square]
  def usedDigits: Buffer[Int] = squares.map( square => square.value).filter( _ != 0)
  def addSquare(square:Square) :Unit
  def validate() =usedDigits.distinct.length == usedDigits.length




class Row(puzzle: Puzzle,val position: Int) extends Iterable[Square] with Area:
  val squares:Buffer[Square]= Buffer[Square]()
  def iterator = squares.iterator
  override def addSquare(square: Square): Unit =
    this.squares += square
    square.row = Some(this)

end Row


class Box(puzzle: Puzzle, val position: Int) extends Iterable[Square] with Area:
  val squares:Buffer[Square]= Buffer[Square]()
  def iterator = squares.iterator
  override def addSquare(square: Square): Unit =
    this.squares += square
    square.box = Some(this)

end Box


class Column(puzzle: Puzzle, val position: Int) extends Iterable[Square]with Area:
  val squares:Buffer[Square]= Buffer[Square]()

  def iterator = squares.iterator

  override def addSquare(square: Square): Unit =
    this.squares += square
    square.column = Some(this)

end Column


class SubArea(puzzle: Puzzle,val sum:Int) extends Iterable[Square] with Area:

  val squares:Buffer[Square]= Buffer[Square]()
  
  var color:Some[Color]  = Some(Color.White)

  var possibleColor : Vector[Color] = Vector(Color.SkyBlue,Color.OrangeRed,Color.DarkOliveGreen,Color.BlueViolet)

  def currentSum = squares.foldLeft(0)( (current ,next) => current + next.value)
  
  def iterator = squares.iterator

  override def addSquare(square: Square): Unit =
    this.squares += square
    square.subArea = Some(this)

  def neigbor: Vector[SubArea] =
    this.squares.flatMap( square => square.neighbor()).filter( square => square.subArea.get != this).map( square => square.subArea.get).distinct.toVector

  def updatePossibleColor() =
    val usedColor =this.neigbor.filter( x=> x.color.isDefined).map( x => x.color.get)
    this.possibleColor = this.possibleColor.filter( color=> !usedColor.contains(color))

  def setColor(newColor:Color) =
    this.color = Some(newColor)

  override def validate(): Boolean =  this.currentSum < sum
  
end SubArea










