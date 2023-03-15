

import org.scalatest.flatspec.AnyFlatSpec
import logic.*
import scala.util.Random
import scala.util.Random.nextInt


/**
 * The pupose of this tet is to check if the logic of programm work properly given a valid save file
 */
class LogicSpec extends AnyFlatSpec :
  val gameObj = Sodoku
