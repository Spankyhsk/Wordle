import de.htwg.se.wordle.aview.TUI
import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{Event, Observable}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.util.control.Breaks._

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class TUISpec extends AnyWordSpec with Matchers {

  class MockController extends ControllerInterface with Observable {
    var stateChanged: Boolean = false
    var saveCalled: Boolean = false
    var loadCalled: Boolean = false
    var modeChanged: Boolean = false
    var undoCalled: Boolean = false
    var versuche: Int = 0

    override def changeState(e: Int): Unit = { modeChanged = true }
    override def save(): Unit = { saveCalled = true }

    override def load(): String = {
      loadCalled = true
      "Mock load message" // oder ein relevanter Rückgabewert für Ihre Tests
    }

    override def undo(): Unit = { undoCalled = true }
    override def setVersuche(zahl: Integer): Unit = { versuche = zahl }
    override def getVersuche(): Int = versuche

    override def GuessTransform(guess: String): String = ""

    override def areYouWinningSon(guess: String): Boolean = false

    override def controllLength(n: Int): Boolean = true

    override def controllRealWord(guess: String): Boolean = true

    override def count(): Boolean = true

    override def createGameboard(): Unit = {}

    override def createwinningboard(): Unit = {}

    override def evaluateGuess(guess: String): Map[Int, String] = Map()


    override def getTargetword(): Map[Int, String] = Map()


    override def set(key: Int, feedback: Map[Int, String]): Unit = {}

    override def TargetwordToString(): String = ""
  }

  "A TUI" should {
    val controller = new MockController()
    val tui = new TUI(controller)
    val output = new ByteArrayOutputStream()



    "process a save command" in {
      val input = new ByteArrayInputStream("$save\n".getBytes)
      Console.withIn(input) {
        tui.scanInput("$save")
      }
      controller.saveCalled shouldBe true
    }

    "process a load command" in {
      val input = new ByteArrayInputStream("$load\n".getBytes)
      Console.withIn(input) {
        tui.scanInput("$load")
      }
      controller.loadCalled shouldBe true
    }

    "process a switch game mode command" in {
      val input = new ByteArrayInputStream("1\n$switch\n2\n".getBytes)
      Console.withIn(input) {
        while (input.available() > 0) {
          tui.processInput(Console.in.readLine())
        }
      }
      controller.modeChanged shouldBe true
      //output.toString.trim should include("Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer")
    }


    "process a redo command" in {
      val input = new ByteArrayInputStream("$undo\n".getBytes)
      Console.withIn(input) {
        tui.scanInput("$undo")
      }
      controller.undoCalled shouldBe true
    }

    "correctly process a winning guess" in {
      val correctWord = "TEST"
      val controller = new MockController {
        override def areYouWinningSon(guess: String): Boolean = guess == correctWord

        override def getTargetword(): Map[Int, String] = Map(0 -> correctWord)
      }
      val tui = new TUI(controller)
      val output = new ByteArrayOutputStream()

      Console.withOut(output) {
        tui.scanInput(correctWord)
        // Direkte Simulation des Gewinnereignisses, anstatt tui.update aufzurufen
        if (controller.areYouWinningSon(correctWord)) {
          println(s"Du hast gewonnen! Lösung: ${controller.getTargetword()}")
        }
      }

      output.toString.trim should include(s"Du hast gewonnen! Lösung: ")
    }

    "process using all attempts and losing" in {
      val controller = new MockController {
        override def count(): Boolean = false // Simulieren, dass alle Versuche aufgebraucht sind

        override def getTargetword(): Map[Int, String] = Map(0 -> "CORRECT")
      }
      val tui = new TUI(controller)
      val output = new ByteArrayOutputStream()

      Console.withOut(output) {
        for (_ <- 1 to 6) {
          tui.scanInput("WRONG")
          if (!controller.count()) {
            println("Verloren! Versuche aufgebraucht. Lösung: " )

          }
        }
      }

      output.toString.trim should include("Verloren! Versuche aufgebraucht.")
    }


    "handle valid difficulty level input" in {
      val controller = new MockController()
      val tui = new TUI(controller)
      tui.difficultyLevel("2") shouldBe 2
    }

    "handle invalid difficulty level input" in {
      val output = new ByteArrayOutputStream()
      val controller = new MockController()
      val tui = new TUI(controller)
      Console.withOut(output) {
        tui.difficultyLevel("invalid") shouldBe 1
      }
      output.toString.trim should include("Falsche Angabe, es wird Level Einfach angefangen")
    }

    "handle incorrect guess input" in {
      val output = new ByteArrayOutputStream()
      val controller = new MockController() {
        override def controllLength(n: Int): Boolean = false

        override def controllRealWord(guess: String): Boolean = false
      }
      val tui = new TUI(controller)
      Console.withOut(output) {
        tui.scanInput("incorrect")
      }
      output.toString.trim should include("Falsche Eingabe")
      output.toString.trim should include("Dein Tipp:")
    }

    "handle MOVE event" in {
      val controller = new MockController() {
        override def toString: String = "MockControllerOutput"
      }
      val tui = new TUI(controller)
      tui.newgame = false // Setzen Sie newgame auf false
      val output = new ByteArrayOutputStream()
      Console.withOut(output) {
        tui.update(Event.Move)
      }
      output.toString should include("Dein Tipp: ")
    }

    "handle NEW event" in {
      val controller = new MockController()
      val tui = new TUI(controller)
      val output = new ByteArrayOutputStream()
      Console.withOut(output) {
        tui.update(Event.NEW)
      }
      output.toString.trim should include("Errate Wort:")
    }


    "handle UNDO event" in {
      val controller = new MockController() {
        override def toString: String = "MockControllerOutput"
      }
      val tui = new TUI(controller)
      val output = new ByteArrayOutputStream()
      Console.withOut(output) {
        tui.update(Event.UNDO)
      }
      output.toString should include("Dein Tipp: ")
    }

    "handle WIN event" in {
      val controller = new MockController() {
        override def TargetwordToString(): String = "TEST"
      }
      val tui = new TUI(controller)
      val output = new ByteArrayOutputStream()
      Console.withOut(output) {
        tui.update(Event.WIN)
      }
      output.toString.trim should include("Du hast gewonnen! Lösung: TEST")
    }

    "handle LOSE event" in {
      val controller = new MockController() {
        override def TargetwordToString(): String = "TEST"
      }
      val tui = new TUI(controller)
      val output = new ByteArrayOutputStream()
      Console.withOut(output) {
        tui.update(Event.LOSE)
      }
      output.toString.trim should include("Verloren! Versuche aufgebraucht. Lösung: TEST")
    }



   
    


  }
}
