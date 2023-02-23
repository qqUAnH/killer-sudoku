package logic


import scala.collection.mutable.Buffer
import scala.collection.Iterator

trait Iterator[Square]:
  def hasNext:Boolean

  def next():Square
end Iterator

trait Area:
  val squares:Buffer[Square]
  def usedDigits: Buffer[Int] = squares.map( square => square.value)
  def addSquare(square:Square) :Unit
  def validate(number :Int) : Boolean




class Row(puzzle: Puzzle,val position: Int) extends Iterable[Square] with Area:
  val squares:Buffer[Square]= Buffer[Square]()
  def iterator = squares.iterator
  override def addSquare(square: Square): Unit =
    this.squares += square
    square.row = this

  override def validate(number: Int): Boolean =  number < 10 && number > 0 && !usedDigits.contains(number) 
end Row


class Box(puzzle: Puzzle, val position: Int) extends Iterable[Square] with Area:
  val squares:Buffer[Square]= Buffer[Square]()
  def iterator = squares.iterator
  override def addSquare(square: Square): Unit =
    this.squares += square
    square.box = this
  override def validate(number: Int): Boolean =  number < 10 && number > 0 && !usedDigits.contains(number) 
end Box


class Column(puzzle: Puzzle, val position: Int) extends Iterable[Square]with Area:
  val squares:Buffer[Square]= Buffer[Square]()
  def iterator = squares.iterator
  override def addSquare(square: Square): Unit =
    this.squares += square
    square.column = this
  override def validate(number: Int): Boolean =  number < 10 && number > 0 && !usedDigits.contains(number) 
end Column


class SubArea(puzzle: Puzzle,val sum:Int) extends Iterable[Square] with Area:
  val squares:Buffer[Square]= Buffer[Square]()

  def currentSum = squares.foldLeft(0)( (current ,next) => current + next.value)
  
  def iterator = squares.iterator
  override def addSquare(square: Square): Unit =
    this.squares += square
    square.subArea = this
  
  override def validate(number: Int): Boolean =  number < 10 && number > 0 && ( currentSum -number) > 0
  
end SubArea










