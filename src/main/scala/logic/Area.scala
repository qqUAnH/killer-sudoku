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

  val colorPlate = Vector(Color.LightSkyBlue,Color.Coral,Color.SpringGreen,Color.PaleVioletRed)

  var possibleColor : Vector[Color] = Vector(Color.LightSkyBlue,Color.Coral,Color.SpringGreen,Color.PaleVioletRed)

  def currentSum = squares.foldLeft(0)( (current ,next) => current + next.value)
  
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

  override def validate(): Boolean =  this.currentSum < sum
end SubArea










