package wordle

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import scala.io.StdIn
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class wordlespec extends AnyWordSpec with Matchers {

  "Testo" should {

    // Konsolenausgaben kommen in Puffer und wird als Zeichenkette ausgegebn
    def erfassenKonsoleOutput(block: => Unit): String = {
      val outputStream = new ByteArrayOutputStream()
      Console.withOut(outputStream) {
        block
      }
      outputStream.toString
    }

    "ask for word length" in {
      val input = "3\n"
      val output = erfassenKonsoleOutput {
        Console.withIn(new ByteArrayInputStream(input.getBytes("UTF-8"))) {
          Testo.askForWordLength()
        }
      }
      output.trim should be("Wähle eine Wortlänge: (1 bis 5):")
    }

    "ask for max attempts" in {
      val input = "5\n"
      val output = erfassenKonsoleOutput {
        Console.withIn(new ByteArrayInputStream(input.getBytes("UTF-8"))) {
          Testo.askForMaxAttempts()
        }
      }
      output.trim should be("Wähle die Anzahl deiner Versuche:")
    }

    "select a random word" in {
      val wordsByLength = Map(
        3 -> Array("abc", "bcd", "cde", "def", "efg")
      )
      val word = Testo.selectRandomWord(wordsByLength(3))
      wordsByLength(3) should contain(word)
    }

    "evaluate guess and display feedback" in {
      val targetWord = "abc"
      val display = "___"
      val guess = "abc"
      val feedback = erfassenKonsoleOutput {
        Testo.evaluateGuess(targetWord, display, guess)
      }
      feedback.trim should be("Dein Tipp: \u001B[32ma\u001B[0m\u001B[32mb\u001B[0m\u001B[32mc\u001B[0m")
    }

    "play Wordle" in {
      val input = "abc\n"
      val output = erfassenKonsoleOutput {
        val targetWord = "abc"
        val display = "___"
        val maxAttempts = 5
        Console.withIn(new ByteArrayInputStream(input.getBytes("UTF-8"))) {
          Testo.playWordle(targetWord, display, maxAttempts)
        }
      }
      output.trim should include("Glückwunsch! Du hast das Wort erraten: abc")
    }

  }
}
