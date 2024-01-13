
/*
import de.htwg.se.wordle.aview.TUI
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.util.{Event, Observable}

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class TUISpec extends AnyWordSpec with Matchers {

  class MockController extends ControllerInterface with Observable {
    var stateChanged: Boolean = false

    override def count(n: Int): Boolean = true
    override def controllLength(n: Int): Boolean = true
    override def controllRealWord(guess: String): Boolean = true
    override def createGameboard(): Unit = notifyObservers(Event.Move)
    override def set(key: Int, feedback: Map[Int, String]): Unit = {}
    override def undo(): Unit = {}
    override def evaluateGuess(guess: String): Map[Int, String] = Map()
    override def toString: String = ""
    override def changeState(e: Int): Unit = notifyObservers(Event.Move)
    override def getTargetword(): Map[Int, String] = Map()
    override def getLimit(): Int = 0
    override def createwinningboard(): Unit = {}
    override def areYouWinningSon(guess: String): Boolean = true
    override def GuessTransform(guess: String): String = ""
  }

  "A TUI" when {
    "initialized" should {
      "run the game loop" in {
        val input = new ByteArrayInputStream("1\nquit\n".getBytes)
        val output = new ByteArrayOutputStream
        val tui = new TUI(new MockController)
        Console.withIn(input) {
          Console.withOut(output) {
            tui.run()
          }
        }
        val expectedOutput = "Willkommen zu Wordle\n" +
          "Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer\n"// Expected output based on the provided TUI code
        output.toString.trim should include(expectedOutput.trim)
      }
    }

    "receiving input" should {
      "handle undo command" in {
        val input = new ByteArrayInputStream("1\n$\nquit\n".getBytes)
        val output = new ByteArrayOutputStream
        val tui = new TUI(new MockController)
        Console.withIn(input) {
          Console.withOut(output) {
            tui.run()
          }
        }
        val expectedOutput = "Willkommen zu Wordle\n" +
          "Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer\n" // Expected output based on the provided TUI code
        output.toString.trim should include(expectedOutput.trim)
        tui.stepback should be(false)
      }

      "handle valid guess" in {
        val input = new ByteArrayInputStream("1\naa\nquit\n".getBytes)
        val output = new ByteArrayOutputStream
        val tui = new TUI(new MockController)
        Console.withIn(input) {
          Console.withOut(output) {
            tui.run()
          }
        }
        val expectedOutput1 = "Willkommen zu Wordle\n" +
          "Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer\n"
           // Updated game board after guess
           val expectedOutput2= "Du hast gewonnen! Lösung:"// Expected output for a winning guess
        output.toString.trim should include(expectedOutput1.trim)
        output.toString.trim should include(expectedOutput2.trim)
      }

      // Add more test cases for handling other input scenarios
    }

    "updating game state" should {
      "notify observers on state change" in {
        val mockController = new MockController
        val tui = new TUI(mockController)
        tui.update(Event.Move)
        mockController.stateChanged should be(false)
      }

      "create new game when receiving NEW event" in {
        val mockController = new MockController
        val tui = new TUI(mockController)
        val input = new ByteArrayInputStream("1\ntest\n".getBytes)
        Console.withIn(input) {
          tui.update(Event.NEW)
        }
        // Add assertions based on your specific TUI behavior
        // For example, check if a new game is created and prompts are displayed.
      }
    }
  }
}




/*
package de.htwg.se.wordle.aview

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemodeComponnent.gamemode
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class TuiSpec extends AnyWordSpec with Matchers {

  "TUI" when {

    "run" should {
      "print the initial game state" in {
        val controller = new MockController("word")
        val tui = new TUI(controller)
        val input = "word\nquit\n"
        val output = captureOutputWithInput(input) {
          tui.run()
        }
        output should include("Errate Wort:")
        output should include("____")
      }

      "end the game with a loss when out of attempts" in {
        val controller = new MockController("word")
        val tui = new TUI(controller)
        val input = "wrong\nquit\n"
        val output = captureOutputWithInput(input) {
          tui.run()
        }
        output should include("Verloren!")
        output should include("Versuche aufgebraucht.")
      }

      "end the game with a win when the correct word is guessed" in {
        val controller = new MockController("WORD")
        val tui = new TUI(controller)

        val input = "word\n"
        val output = captureOutputWithInput(input) {
          tui.run()
        }
        output should include("Du hast gewonnen!")
      }

    }

    "inputLoop" should {
      "continue the loop when continue is true" in {
        val controller = new MockController("word")
        val tui = new TUI(controller)
        val input = "wrong\nwrong\nwrong\nquit\n" // Added 'quit' to end the loop
        val output = captureOutputWithInput(input) {
          tui.inputLoop(1)
        }
        output should include("WRONG")
        output should include("WRONG\nWRONG")
        output should include("WRONG\nWRONG\nWRONG")
      }
    }

    "scanInput" should {
      "exit the program when 'quit' is entered" in {
        val controller = new MockController("word")
        val tui = new TUI(controller)
        val input = "quit"

        val output = captureOutputWithInput(input) {
          tui.scanInput(input, 1)
        }

        output should include("Wiedersehen")
        tui.continue should be(false)
      }
    }

  }

  def captureOutputWithInput(input: String)(block: => Unit): String = {
    val stream = new ByteArrayOutputStream()
    Console.withOut(stream) {
      Console.withIn(new ByteArrayInputStream(input.getBytes()))(block)
    }
    stream.toString
  }

  class MockController(target: String) extends controll(gamemode.gamemode1()) {
    var continue = true

    override def count(n: Int): Boolean = {
      continue
    }

    override def evaluateGuess(guess: String): Map[Int, String] = {
      val keys: List[Int] = getTargetword().keys.toList
      val feedback: Map[Int, String] = keys.map(key => key -> gamemech.evaluateGuess(getTargetword()(key), guess)).toMap
      feedback
    }

    def quit(): Unit = {
      continue = false
    }

    val targetword: String = target
  }


}
*/
*/