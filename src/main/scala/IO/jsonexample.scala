import scala.collection.mutable.Buffer
import scala.util.{Try, Failure, Success}
import java.io.{FileReader, BufferedReader, FileNotFoundException}

import io.circe.generic.auto.*
import io.circe.syntax.*
import io.circe.parser.decode


@main
def jsonExample() =

  val jsonString = """
  [
    {
        "id":"5ef0a9a92359ffa63a9cb56e",
        "age":25,
        "name":"Georgette Tillman",
        "gender":"female",
        "pets":[
            {
                "age":4,
                "name":"Gibson"
            },
            {
                "age":2,
                "name":"Mr. Harrington"
            }
        ]
    },
    {
        "id":"5ef0a9a946a00ec035b75f09",
        "age":22,
        "name":"Duffy Fulton",
        "gender":"male",
        "pets":[
            {
                "age":1,
                "name":"Mills"
            }
        ]
    }
  ]
  """

  /**Circe is able to use the following definitions to automatically
  decode correctly formatted JSON*/
  case class Person(id: String, name: String, age: Int, gender: String, pets: List[Pet])
  case class Pet(name: String, age: Int)

  /** Reads in a JSON formatted string as a List if possible*/
  def readJson(text: String ) = decode[List[Person]](text).toTry

  /** Reads all the contents of a given Source as a String */
  def readText(source: scala.io.Source ) = source.getLines().mkString("\n")

  /** Note: In larger programs, source would be closed manually */
  val fileSource   = Try(scala.io.Source.fromFile("foo.bar"))
  val stringSource = Try(scala.io.Source.fromString(jsonString))

  val people =
    val temp = Buffer[Person]()
    for
      source <- stringSource     // Get successful source result
      text   =  readText(source) //Get text from source
      list   <- readJson(text)   //Parse JSON into list
      person <- list
    do
      temp += person
    temp

  println(people)

end jsonExample