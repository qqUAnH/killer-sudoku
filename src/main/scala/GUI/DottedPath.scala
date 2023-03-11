package GUI

import logic._
import scalafx.geometry.Point2D
import scalafx.scene.shape.Line
import scala.collection.mutable.Buffer
import scalafx.scene.paint.Color.Black



object DottedPath:
  def getPoints(square: Square): Vector[Point2D] =
    val position = square.position
    val row = position / 9
    val column = position % 9
    val topLeft = new Point2D(column * squareLength + 1, row * 9 + 1)
    val topRight = new Point2D((column + 1) * squareLength - 1, row * 9 + 1)
    val botLeft = new Point2D(column * squareLength + 1, (row + 1) * 9 - 1)
    val botRight = new Point2D((column + 1) * squareLength - 1, (row + 1) * 9 - 1)
    Vector(topLeft, topRight, botLeft, botRight)

  def drawLine(A: Point2D,B:Point2D) =
    new Line:
        stroke = Black
        strokeWidth = 2
        startX = A.x
        startY = A.y
        endX = B.x
        endY = B.y


  def allLine( points: Vector[Point2D]):Vector[Line]=
    var result:Vector[Line] = Vector()
    for i <- 0 until( points.length) do
      val helper = points.filter( P=>  !( P.x < points(i).x && P.y < points(i).y ) )
      result = result ++ helper.map( drawLine(_,points(i)))
   
    result

  val subAreas =Sodoku.getPuzzle.allSubAreas()
  val result =subAreas.flatMap(_.squares)
    .map( getPoints(_))
    .flatMap( allLine(_))



