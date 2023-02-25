package logic

class Square(var value:Int,val position:Int ,val puzzle: Puzzle) {
  var possibleNumbers  = Vector.tabulate(9)(x=>x+1)
  var row     :Row     = null
  var column  :Column  = null
  var box     :Box     = null
  var subArea :SubArea = null

  def setValue( number : Int) =
    if number == 0 || this.possibleNumbers.contains(number) then
      value = number

  // this function isn't finished
  def updatePossibleNumbers() =
    this.possibleNumbers.filter( number => row.validate(number) && column.validate(number) && box.validate(number) && subArea.validate(number))

  def neighbor(): Vector[Square] =
    Vector(position - 1, position + 1, position - 9, position + 9).filter(x => (x + 1) / 9 == position / 9 && (x - 1) / 9 == position / 9 && x > 0 && x < 81).map(x=> this.puzzle.square(x))
}
