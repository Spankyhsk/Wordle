/*import de.htwg.se.wordle.aview.GUISWING
import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.model.gamemodeComponnent.{GamemodeInterface, gamemode1}
import de.htwg.se.wordle.util.{Event, Observer}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.swing.Reactions
import scala.swing.event.ButtonClicked

class GUISwingSpec extends AnyWordSpec with Matchers {

  class TestController extends ControllerInterface {
    // Implementieren Sie die notwendigen Methoden des ControllerInterface
    // ...

    override def count(): Boolean = true

    override def controllLength(n: Int): Boolean = n == 5

    override def controllRealWord(guess: String): Boolean = guess.forall(_.isLetter)

    override def evaluateGuess(guess: String): Map[Int, String] = Map(1 -> "Feedback")

    override def GuessTransform(guess: String): String = guess.toUpperCase

    override def setVersuche(zahl: Integer): Unit = {}

    override def getVersuche(): Int = 1

    override def areYouWinningSon(guess: String): Boolean = false

    override def createwinningboard(): Unit = {}

    override def createGameboard(): Unit = {}

    override def toString: String = "Spielzustand"

    override def changeState(e: Int): Unit = {}

    override def getTargetword(): Map[Int, String] = Map(1 -> "Wort")

    override def TargetwordToString(): String = "Wort1: Wort"

    override def save(): Unit = {}

    override def load(): String = "Spiel geladen"

    override def set(key: Int, feedback: Map[Int, String]): Unit = {}

    override def undo(): Unit = {}

    override def getGamemode(): GamemodeInterface = ???

    override def setTargetWord(targetWordMap: Map[Int, String]): Unit = {}

    override def setLimit(Limit: Int): Unit = {}

    override def getLimit(): Int = 6
  }

  "A GUISWING object" should {
    val testController = new TestController
    val gui = new GUISWING(testController)

    "initialize correctly" in {
      gui.title shouldBe "Wordle"
      gui.resizable shouldBe true
      // Weitere Überprüfungen auf Initialisierung
    }

    "handle button clicks" in {
      gui.listenTo(gui.EasymodusButton, gui.MediummodusButton, gui.HardmodusButton)
      gui.reactions += {
        case ButtonClicked(button) =>
        // Implementieren Sie Logik zur Überprüfung der Reaktionen
      }

      // Simulieren von Button-Klicks
      gui.EasymodusButton.doClick()
      gui.MediummodusButton.doClick()
      gui.HardmodusButton.doClick()

      // Überprüfen Sie, ob die entsprechenden Aktionen ausgeführt wurden
    }

    "update its state on events" in {
      // Erstellen von Event-Tests
      gui.update(Event.NEW)
      gui.update(Event.WIN)
      gui.update(Event.LOSE)
      gui.update(Event.UNDO)

      // Überprüfen Sie, ob die GUI wie erwartet auf die Events reagiert hat
    }

    "update output correctly" in {
      gui.upgradeOutput()
      // Prüfen Sie, ob das OutputPanel aktualisiert wurde
    }

    "reset input field correctly" in {
      gui.resetInputField()
      gui.inputTextField.text shouldBe ""
    }

    "handle game mode button clicks" in {
      gui.EasymodusButton.doClick()
      // Überprüfen, ob der Zustand nach Klick auf EasymodusButton korrekt ist

      gui.MediummodusButton.doClick()
      // Überprüfen, ob der Zustand nach Klick auf MediummodusButton korrekt ist

      gui.HardmodusButton.doClick()
      // Überprüfen, ob der Zustand nach Klick auf HardmodusButton korrekt ist
    }

    "update game mode panel correctly" in {
      gui.upgradegamemoduspanel(gui.EasymodusButton)
      // Überprüfen Sie, ob die Farben und Inhalte des gamemoduspanel aktualisiert wurden
    }

    "update button colors correctly" in {
      gui.updateButtonColors(gui.EasymodusButton)
      // Überprüfen Sie, ob die Farbe von EasymodusButton korrekt aktualisiert wurde
    }

    "update the GUI on different events" in {
      gui.update(Event.NEW)
      // Überprüfen Sie, ob die GUI korrekt auf das NEW-Event reagiert

      gui.update(Event.WIN)
      // Überprüfen Sie, ob die GUI korrekt auf das WIN-Event reagiert

      gui.update(Event.LOSE)
      // Überprüfen Sie, ob die GUI korrekt auf das LOSE-Event reagiert

      gui.update(Event.UNDO)
      // Überprüfen Sie, ob die GUI korrekt auf das UNDO-Event reagiert
    }

    "handle text input correctly" in {
      gui.inputTextField.text = "guess"
      gui.reactions(Reactions.EditDone(gui.inputTextField))
      // Überprüfen Sie, ob die Eingabe korrekt verarbeitet wird
    }

    "handle game mode selection correctly" in {
      // Überprüfen Sie, ob die Auswahl des Spielmodus korrekt behandelt wird
      gui.EasymodusButton.doClick()
      testController.getGamemode() shouldBe gamemode1
      // ... Ähnliche Tests für MediummodusButton und HardmodusButton
    }

    "render panels correctly" in {
      // Überprüfen Sie, ob die Panels korrekt gerendert werden
      gui.northpanel should not be null
      gui.centerPanel should not be null
      gui.southPanel should not be null
    }

  }
}
*/
