package IO
import scala.collection.mutable.Buffer
import io.circe.syntax.*
import io.circe._
import io.circe.generic.auto._
import logic._

object JSON {
  implicit val SquareEncoder : Encoder[Square] = square => Json.obj(
    "value"   -> square.value.asJson ,
    "position"-> square.position.asJson
  )

  implicit val squareDecoder : Decoder[Square] = Decoder.forProduct2("value","position")(Square.apply)

  implicitly[Encoder[SubArea]]
  implicitly[Decoder[SubArea]]

  def decode( subAreas: Buffer[SubArea] ) =
    subAreas.asJson.spaces2

  
}

