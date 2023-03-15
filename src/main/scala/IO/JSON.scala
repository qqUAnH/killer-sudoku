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


//  https://stackoverflow.com/questions/17521364/scala-writing-json-object-to-file-and-reading-it
// https://docs.oracle.com/javase/7/docs/api/java/io/File.html#separatorChar
object JSON {
  val saveFolder = os.pwd / os.RelPath("Save_File")
  implicit val SquareEncoder : Encoder[Square] = square => Json.obj(
    "value"   -> square.value.asJson ,
    "position"-> square.position.asJson
  )

  implicit val squareDecoder : Decoder[Square] = Decoder.forProduct2("value","position")(Square.apply)

  implicitly[Encoder[SubArea]]
  implicitly[Decoder[SubArea]]
  def save( subAreas: Buffer[SubArea]) =
    val result =subAreas.asJson.spaces2
    result
    Some(new PrintWriter( "/home/quan-hoang/Downloads/killer-sodoku3/src/main/scala/IO/savefile/savefile1.txt")).foreach { p =>
    p.write(result);   p.close

    try
      os.write( saveFolder / "savefile1.txt",result)
    catch
      case _ => {
        val file = new File(saveFolder.toString++"fail")

      }

  }
// Try catch stuff
  def list() =
    os.list(os.pwd)


  def load(filename:String):Vector[SubArea]  =
    val data = os.read(saveFolder / filename)
    val parseResult: Either[ParsingFailure, Json] = parse(data)
    parseResult match
      case Left(parsefail) => throw IOException("msg")
      case Right(json) => {
        val allsubArea = json.asArray.get.map( x=> x.as[SubArea])
        allsubArea.map(_.getOrElse( null)).toVector
    // sourse haven't be closed
      }








  
}

