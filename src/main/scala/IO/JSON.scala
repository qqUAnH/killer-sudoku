package IO
import scala.collection.mutable.Buffer
import io.circe.syntax.*
import io.circe._
import io.circe.generic.auto._
import logic._
import io.circe.parser._
import java.io.IOException
import java.io._


//  https://stackoverflow.com/questions/17521364/scala-writing-json-object-to-file-and-reading-it
object JSON {
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
  }
// Try catch stuff
  def load(path:String):Vector[SubArea]  =
    val source = scala.io.Source.fromFile(path)
    val data = source.getLines.mkString
    val parseResult: Either[ParsingFailure, Json] = parse(data)
    source.close()
    parseResult match
      case Left(parsefail) => throw IOException("msg")
      case Right(json) => {
        val allsubArea = json.asArray.get.map( x=> x.as[SubArea])
        allsubArea.map(_.getOrElse( null)).toVector
    

    // sourse haven't be closed
      }

  def writeToFile(fileName: String, arr: Seq[String]) =
    try
      val filewriter = new FileWriter(fileName)
      val bufferWriter = new BufferedWriter(filewriter)
      try
        for i <- arr do
          bufferWriter.write(i)

          bufferWriter.newLine()
      finally
        bufferWriter.close()
        filewriter.close()
    catch

      case e: FileNotFoundException => println("not found file")

      case e: IOException => println("io")






  
}

