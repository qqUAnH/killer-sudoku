
import org.scalatest.flatspec.AnyFlatSpec
import logic.*
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
    val allSubArea = Sodoku.getPuzzle.allSubAreas()
    assert( allSubArea.forall( _.squares.map(_.color).distinct.length ==1))
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
  "updateValue" should "change possibleNumber in all square in the same row and collumn"
    for square <- allSquares do
      val newValue = Random.nextInt(9)+1
      square.setValue( newValue)
      square.row.forall(_.forall( !_.possibleNumbers.contains(newValue)))
      square.box.forall(_.forall( !_.possibleNumbers.contains(newValue)))
      square.column.forall(_.forall( !_.possibleNumbers.contains(newValue)))
      square.setValue(0)






