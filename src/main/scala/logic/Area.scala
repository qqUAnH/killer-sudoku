package logic
import scala.collection.mutable.Buffer
import scala.collection.Iterator
import scalafx.scene.paint.Color
import scala.util.Random

trait Iterator[Square]:
  def hasNext:Boolean

  def next():Square
end Iterator

trait Area(  squares:Vector[Square]) extends Iterable[Square]:
  def iterator = squares.iterator
  val alphabet  :Vector[Int] = Vector.tabulate(9)(_ + 1)
  def usedDigits:Vector[Int] = squares.map( square => square.value).filter( _ != 0)
  def validate()  :Boolean     = usedDigits.distinct.length == usedDigits.length
  def isFilled  :Boolean     = squares.forall(_.value != 0)
  def addSquares():Unit =
    this.squares.foreach(square => square.addArea(this))
end Area


case class Row( squares:Vector[Square],position:Int) extends  Area(squares:Vector[Square]):
end Row


case class Box( squares:Vector[Square],  position: Int) extends  Area(squares:Vector[Square]):
end Box


case class Column(squares:Vector[Square], val position: Int) extends  Area(squares:Vector[Square]):
end Column


case class SubArea( squares:Vector[Square],sum:Int ) extends Area(squares:Vector[Square]):
  var color:Option[Color]  = None
  private val colorPlate = Vector(Color.LightSkyBlue,Color.Coral,Color.SpringGreen,Color.PaleVioletRed).map(_.brighter)
  var possibleColor : Vector[Color] = Vector(Color.LightSkyBlue,Color.Coral,Color.SpringGreen,Color.PaleVioletRed)
  var possibleComb         :Int = 0

  def currentSum :Int =  squares.foldLeft(0)( (current ,next) => current + next.value)
  def numberOfEmptySquares :Int = squares.count( _.value ==0)

  
  private def calculatePossibleCombination( numberOfSquares:Int,alphabet:Vector[Int],sum:Int):Int =
    if numberOfSquares == 0 then
      0
    else if numberOfSquares == 1 then
      if alphabet.contains(sum) then 1 else 0
    else if alphabet.length  < numberOfSquares then
      0
    else
      calculatePossibleCombination(numberOfSquares, alphabet.drop(1), sum)
        + calculatePossibleCombination(numberOfSquares - 1, alphabet.drop(1), sum - alphabet(0))
      
  def updatePossibleComb():Unit =
    possibleComb= calculatePossibleCombination(numberOfEmptySquares , alphabet , sum-currentSum)

  def neighbour: Vector[SubArea] =
    this.squares.flatMap(square => square.neighbor()).filter(square => square.getSubArea.get != this).map(square => square.getSubArea.get).distinct.toVector

  def updatePossibleColor() =
    val usedColor = this.neighbour.filter(x => x.color.isDefined).map(x => x.color.get)
    this.possibleColor = this.colorPlate.filter(color => !usedColor.contains(color))

  def newColor() =
    val index = Random.nextInt(possibleColor.length)
    this.color = Some(possibleColor(index))
    
  override def validate(): Boolean =
    if this.numberOfEmptySquares == 0 then
     this.currentSum == sum && super.validate()
    else
     this.currentSum <= sum && super.validate()

end SubArea

// Is this necessary>
object SubArea:
  def apply(squares: Vector[Square] ,sum:Int):SubArea=
    new SubArea(squares, sum)










