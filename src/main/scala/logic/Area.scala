package logic


import scala.collection.mutable.Buffer
import scala.collection.Iterator
import scalafx.scene.paint.Color
import scala.util.Random

trait Iterator[Square]:
  def hasNext:Boolean

  def next():Square
end Iterator

trait Area( squares:Vector[Square]):
  def usedDigits: Vector[Int] = squares.map( square => square.value).filter( _ != 0)
  def validate()   = usedDigits.distinct.length == usedDigits.length
  def addSquares() = this.squares.foreach( square => square.addArea(this))


case class Row( squares:Vector[Square],position:Int) extends Iterable[Square] with Area(squares:Vector[Square]):
  def iterator = squares.iterator
end Row


case class Box( squares:Vector[Square],  position: Int) extends Iterable[Square] with Area(squares:Vector[Square]):
  def iterator = squares.iterator
end Box


case class Column(squares:Vector[Square], val position: Int) extends Iterable[Square]with Area(squares:Vector[Square]):
  def iterator = squares.iterator
end Column


case class SubArea( squares:Vector[Square],sum:Int ) extends Iterable[Square] with Area(squares:Vector[Square]):
  var color:Option[Color]  = None

  val alphabet   = Vector.tabulate(9)(_+1)

  val colorPlate = Vector(Color.LightSkyBlue,Color.Coral,Color.SpringGreen,Color.PaleVioletRed).map(_.brighter)

  var possibleColor : Vector[Color] = Vector(Color.LightSkyBlue,Color.Coral,Color.SpringGreen,Color.PaleVioletRed)

  def currentSum :Int = squares.foldLeft(0)( (current ,next) => current + next.value)

  def numberOfEmptySquares :Int = squares.filter( _.value ==0 ).length


  
  def iterator = squares.iterator


  def neigbor: Vector[SubArea] =
    this.squares.flatMap( square => square.neighbor()).filter( square => square.subArea.get != this).map( square => square.subArea.get).distinct.toVector

  def updatePossibleColor() =
    val usedColor =this.neigbor.filter( x=> x.color.isDefined).map( x => x.color.get)
    this.possibleColor = this.colorPlate.filter( color => !usedColor.contains(color))

  def newColor() =
    val index  =Random.nextInt(possibleColor.length)
    println(""+possibleColor.length+"   " +index +"   "+sum)
    this.color = Some(possibleColor(index))

  def numberOfPossibleCombination( numberOfSquares:Int,alphabet:Vector[Int],sum:Int):Int =
    if numberOfSquares == 0 then
      0
    else if numberOfSquares == 1 then
      if alphabet.contains(sum) then 1 else 0
    else if alphabet.length > 1 then
      numberOfPossibleCombination( numberOfSquares ,alphabet.drop(1),sum)
        + numberOfPossibleCombination(numberOfSquares-1 ,alphabet.drop(1) , sum -alphabet(0))
        + numberOfPossibleCombination(numberOfSquares-1 ,alphabet , sum -alphabet(0))
    else
      numberOfPossibleCombination(numberOfSquares-1 ,alphabet , sum -alphabet(0))


  override def validate(): Boolean =  this.currentSum < sum
end SubArea

object SubArea:
  def apply(squares: Vector[Square] ,sum:Int):SubArea=
    new SubArea(squares, sum)










