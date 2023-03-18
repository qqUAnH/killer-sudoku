package IO
import scala.collection.mutable.Buffer
import io.circe.syntax.*
import io.circe._
import io.circe.generic.auto._
import logic._
import io.circe.parser._
import java.io.IOException
import java.io._
import os.write
import os.read
import os.Path


//  https://stackoverflow.com/questions/17521364/scala-writing-json-object-to-file-and-reading-it
// https://docs.oracle.com/javase/7/docs/api/java/io/File.html#separatorChar
object  JSON {
  val saveFolder = os.pwd / os.RelPath("Save_File")
  implicit val SquareEncoder : Encoder[Square] = square => Json.obj(
    "value"   -> square.value.asJson ,
    "position"-> square.position.asJson
  )
  implicit val squareDecoder : Decoder[Square] = Decoder.forProduct2("value","position")(Square.apply)
  implicitly[Encoder[SubArea]]
  implicitly[Decoder[SubArea]]
  def save( subAreas: Buffer[SubArea],path:Path) =
    try
      val result =subAreas.asJson.spaces2
      os.write.over( path ,result)
    catch
      case _ => {
        println("Invalid Input")
      }
// Try catch stuff
  def list() =
    os.list(saveFolder).map(_.toString.split("/").last) 
    
  def load(path: Path):Option[Vector[SubArea]] =
    try
      val data = os.read(path)
      val parseResult: Either[ParsingFailure, Json] = parse(data)
      val result =parseResult match
        case Left(parsefail) => throw IOException("msg")
        case Right(json) => {
        val allsubArea = json.asArray.get.map( x=> x.as[SubArea])
        allsubArea.map(_.getOrElse( null)).toVector
      }
      Some(result)
    catch
      case e:NullPointerException => None
      case _                      => None
      
}

