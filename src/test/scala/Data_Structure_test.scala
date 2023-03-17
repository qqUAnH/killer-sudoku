
import org.scalatest.flatspec.AnyFlatSpec
import logic._
import scalafx.scene.paint.Color.White

import scala.util.Random
import scala.util.Random.nextInt


//https://www.scalatest.org/user_guide/writing_your_first_test
/**
 * The pupose of this tet is to check if the logic of programm work properly given a valid save file
 */
class LogicSpec extends AnyFlatSpec :
  val gameObj = Sodoku
  val allSquares = Sodoku.getPuzzle.allSquare()
  val allSubAreas = Sodoku.getPuzzle.allSubAreas()
  val Digits  = Vector.tabulate(9)(x => x+1)


  
  // This series of test below will test if the function setupPuzzle works as intended
  "Every square " should "be assigned to a box" in {
    assert( allSquares.forall(_.box.isDefined))
   }
  "Every square " should "be assigned to a row" in {
    assert(allSquares.forall(_.row.isDefined))
  }
  "Every square " should "be assigned to a column" in {
    assert(allSquares.forall(_.column.isDefined))
  }
  "Every square " should "be assigned to a subArea" in {
    assert(allSquares.forall(_.subArea.isDefined))
  }
  "Color of a square" should "be not White ,that is the coloring scheme work properly" in {
    assert(allSquares.forall(_.subArea.forall(_.color.forall(_ != White))))
  }
  "Square in a same SubArea " should "have a same Color" in {
    assert( allSubAreas.forall( _.squares.map(_.color).distinct.length ==1))
  }


  // This series of test below will test some methods of Square object
  "A square " should " have at least two neighbor" in {
    assert(allSquares.forall(_.neighbor().length >= 2))
  }
  "setValue" should " change the Value of a square" in {
    for i <- 0 until 10 do
     allSquares.foreach(_.setValue(i))
     assert(allSquares.forall(_.value ==i))
     allSquares.foreach(_.setValue(0))
  }
  "updatePossibleNumber" should "change possibleNumber in all square in the same row and collumn" in {
    for square <- allSquares do
      val newValue = Random.nextInt(9)+1
      square.setValue( newValue)
      println(newValue)
      println( square.column.get.usedDigits.length + "aa")
      assert(square.row.forall(_.forall( _.isValid)))
      assert(square.box.forall(_.forall( _.isValid)))
      assert(square.column.forall(_.forall(_.isValid )))
      square.setValue(0)
  }

  "numberOfPossibleCombination" should "return correct number" in {
    val execptedValue = Map (
      1 -> Array((1,1), (2,1), (3,1), (4,1), (5,1), (6,1), (7,1), (8,1), (9,1)),
      2 -> Array((3,1), (4,1), (5,2), (6,2), (7,3), (8,3), (9,4), (10,4), (11,4), (12,3), (13,3), (14,2), (15,2), (16,1), (17,1)),
      3 -> Array((6,1), (7,1), (8,2), (9,3), (10,4), (11,5), (12,7), (13,7), (14,8), (15,8), (16,8), (17,7), (18,7), (19,5), (20,4), (21,3), (22,2), (23,1), (24,1)),
      4 -> Array((10,1), (11,1), (12,2), (13,3), (14,5), (15,6), (16,8), (17,9), (18,11), (19,11), (20,12), (21,11), (22,11), (23,9), (24,8), (25,6), (26,5), (27,3), (28,2), (29,1), (30,1)),
      5 -> Array((15,1), (16,1), (17,2), (18,3), (19,5), (20,6), (21,8), (22,9), (23,11), (24,11), (25,12), (26,11), (27,11), (28,9), (29,8), (30,6), (31,5), (32,3), (33,2), (34,1), (35,1))
     )
    def check(subArea: SubArea) =
      val currentSum =subArea.currentSum
      val emptySquare = subArea.numberOfEmptySquares
      val result :Int= subArea.numberOfPossibleCombination( emptySquare , subArea.alphabet ,currentSum)
      val correctSum = execptedValue.apply(emptySquare).filter( (x:Int,y:Int) =>  x == currentSum)
      println(emptySquare + "##")
      println(correctSum.mkString(" "))
      println(result)
      if correctSum.isEmpty then
        true
      else
        correctSum.head._2 == result || result == 0
    allSubAreas.foreach(check(_))
    assert(allSubAreas.forall( check (_)))



  }





