/*package de.htwg.se.wordle

import de.htwg.se.wordle.aview.{GUISWING, TUI}
import de.htwg.se.wordle.controller.ControllerInterface
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import scala.io.StdIn

class WordleSpec extends AnyWordSpec with Matchers {

  "The Wordle main application" when {
    "started" should {
      "initialize all components correctly" in {
        val controller = new ControllerInterface {
          // Implementieren Sie hier die erforderlichen Methoden des ControllerInterface
          override def count(): Boolean = true

          override def controllLength(n: Int): Boolean = true

          override def controllRealWord(guess: String): Boolean = true

          override def evaluateGuess(guess: String): Map[Int, String] = Map()

          override def GuessTransform(guess: String): String = guess.toUpperCase

          override def setVersuche(zahl: Integer): Unit = {}

          override def getVersuche(): Int = 0

          override def areYouWinningSon(guess: String): Boolean = false

          override def createwinningboard(): Unit = {}

          override def createGameboard(): Unit = {}

          override def toString: String = "Game state"

          override def changeState(e: Int): Unit = {}

          override def getTargetword(): Map[Int, String] = Map()

          override def TargetwordToString(): String = "Word"

          override def set(key: Int, feedback: Map[Int, String]): Unit = {}

          override def undo(): Unit = {}

          override def save(): Unit = {}

          override def load(): String = "Loaded"

          override def add(s: de.htwg.se.wordle.util.Observer): Unit = {}

          override def remove(s: de.htwg.se.wordle.util.Observer): Unit = {}

          override def notifyObservers(e: de.htwg.se.wordle.util.Event): Unit = {}
        }

        val tui = new TUI(controller)
        val gui = new GUISWING(controller)

        // Testen, ob die GUI und TUI korrekt initialisiert wurden
        tui should not be null
        gui should not be null
      }
    }

    "started" should {
      "display the welcome message and options for game mode" in {
        val input = new ByteArrayInputStream("quit\n".getBytes) // Simuliert die Eingabe von "$quit" um die Anwendung zu beenden
        val output = new ByteArrayOutputStream()

        Console.withOut(output) {
          Console.withIn(input) {
            wordle.main(Array()) // Ruft die main-Methode auf
          }
        }

        val outputString = output.toString.trim
        outputString should include("Willkommen zu Wordle")
        outputString should include("Befehle")
        outputString should include("$quit := Spiel beenden")
        outputString should include("$save := Speichern")
        outputString should include("$load := Laden")
        outputString should include("$switch := Schwierigkeit verändern")
        outputString should include("Gamemode aussuchen")
      }
    }
    */
  




