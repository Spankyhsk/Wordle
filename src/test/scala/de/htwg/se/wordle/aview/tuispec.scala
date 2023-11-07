package de.htwg.se.wordle.aview
import de.htwg.se.wordle.aview.TUI

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.attempt
import de.htwg.se.wordle.aview.MockController
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}


class tuispec extends AnyWordSpec {

  "TUI" when {

    "run" should {
      "print the initial game state" in {
        val controller = new MockController(3, "word")
        val tui = new TUI(controller)
        val input = "word\nquit\n"
        val output = captureOutputWithInput(input) {
          tui.run()
        }
        output should include("Errate Wort:")
        output should include("____")
      }

      "end the game with a loss when out of attempts" in {
        val controller = new MockController(1, "word")
        val tui = new TUI(controller)
        val input = "wrong\nquit\n"
        val output = captureOutputWithInput(input) {
          tui.run()
        }
        output should include("Verloren!")
        output should include("Versuche aufgebraucht.")
      }

      "end the game with a win when the correct word is guessed" in {
        val controller = new MockController(3, "WORD")
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
        val controller = new MockController(3, "word")
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
        val controller = new MockController(3, "word")
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

}

def captureOutputWithInput(input: String)(block: => Unit): String = {
  val stream = new ByteArrayOutputStream()
  Console.withOut(stream) {
    Console.withIn(new ByteArrayInputStream(input.getBytes()))(block)
  }
  stream.toString
}


class MockController(attempts: Int, target: String) extends controll(attempt(targetword = target, x = attempts)) {
  var continue = true

  override def count(n: Int): Boolean = {
    if (n >= attempts) {
      continue = false
    }
    continue
  }

  override def evaluateGuess(targetWord: String, guess: String): String = {
    val minLen = Math.min(targetWord.length, guess.length) //Vergleicht nur so lange wie kurze Wort Indexfehler beim testen
    val feedback = guess.zipWithIndex.map {
      case (g, i) if i < minLen && g == targetWord(i) => "\u001B[32m" + g + "\u001B[0m" //Tupel mit (buchstabe, index)
      case (g, _) if targetWord.contains(g) => "\u001B[33m" + g + "\u001B[0m"
      case (g, _) => g.toString
    }.mkString("")

    feedback
  }

  var exitCalled: Boolean = false // Eine Variable, um das Programmende nachzuverfolgen

  def quit(): Unit = {
    exitCalled = true // Diese Methode wird aufgerufen, wenn das Programm beendet werden soll
  }


  override val limit: Int = attempts
  override val targetword: String = target


}