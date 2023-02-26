import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.input.{KeyCode, KeyCombination, KeyCodeCombination, KeyEvent}
import scalafx.scene.Scene
import scalafx.stage.Stage

object Main extends JFXApp3 {
  val ctlA = new KeyCodeCombination(KeyCode.A, KeyCombination.ControlDown)

  override def start(): Unit =
    stage = new PrimaryStage {
    scene = new Scene {
      onKeyPressed = { ke =>
        if (ctlA.`match`(ke))
          println("Matches ^A")
        else
          println("No match")
      }
    }
  }
}