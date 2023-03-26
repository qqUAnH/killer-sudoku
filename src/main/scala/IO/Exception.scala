package IO

case class NoFileSelected(msg:String) extends Exception
case class TryToSaveToSourceFile(msg:String) extends Exception
