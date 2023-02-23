package logic

class Square(var value:Int,position:Int) {
  var possibleNumbers  = Vector.tabulate(9)(x=>x+1)
  var row     :Row     = null
  var column  :Column  = null
  var box     :Box     = null
  var subArea :SubArea = null

  def setValue( number : Int) =
    if number == 0 || this.possibleNumbers.contains(number) then
      value = number

  def updatePossibleNumbers() =
    this.possibleNumbers.filter( number => row.validate(number) && column.validate(number) && box.validate(number) && subArea.validate(number))
    
  def neighbor = ???

}
