import org.scalatest.flatspec.AnyFlatSpec
import logic.*
import IO.JSON

import scala.collection.mutable.Buffer
import scala.util.Random
import scala.util.Random.nextInt

class JsonSpec extends AnyFlatSpec:
  val validData = Buffer(Vector(5, 1, 2), Vector(11, 3, 12), Vector(9, 4, 5),
    Vector(18, 6, 15, 16), Vector(12, 7, 8, 17), Vector(22, 9, 18, 27, 26),
    Vector(15, 10, 19), Vector(6, 11, 20), Vector(10, 21, 22),
    Vector(15, 13, 14, 23), Vector(12, 24, 25),
    Vector(12, 28, 37), Vector(14, 29, 30), Vector(3, 31, 32), Vector(9, 33, 34),
    Vector(15, 35, 36), Vector(9, 38, 38 + 9), Vector(6, 39, 40), Vector(16, 41, 42),
    Vector(16, 43, 43 + 9, 43 + 9 - 1, 43 + 9 - 2), Vector(14, 44, 44 + 9), Vector(21, 45, 45 + 9, 45 + 18, 45 + 17),
    Vector(9, 46, 46 + 9), Vector(7, 48, 49), Vector(16, 56, 57), Vector(31, 58, 58 + 9, 58 + 18, 58 + 10, 58 + 19),
    Vector(4, 59, 60), Vector(21, 61, 61 + 9, 61 + 8), Vector(18, 64, 65, 64 + 9, 65 + 9), Vector(9, 66, 66 + 9),
    Vector(3, 71, 72), Vector(8, 80, 81), Vector(9, 78, 79))

  val pathToValidFile = Vector (JSON.saveFolder / "savefile2.txt" , JSON.saveFolder / "savefile4.txt" )
  val pathToInvalidFile = JSON.saveFolder / "Invalidsave.txt"

  "load" should "sucessly parse a valid json file" in {
    assert(pathToValidFile.map( JSON.load(_)).forall( _.isDefined))
  }
  "load" should "fail when try to parse an invalid file" in {
    assert( JSON.load(pathToInvalidFile).isEmpty )
  }
  "load" should "throw execption when the user choose don't choose any file" in {
    assert( JSON.load(null).isEmpty)
  }

  "save" should "throw exeception when the user don't choose any file" in {
    assert( JSON.save(Sodoku.puzzle.getSubAreas, null) == "Save cancelled")
  }
  