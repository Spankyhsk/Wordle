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
