package wordle

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers


class wordlespec extends AnyWordSpec with Matchers {

  "Wordle" should {
    "start a game with a given secret word and handle winning" in {
      val controller = new controll()

      val testSecretWord = "A"
      val winningOutput = captureConsoleOutputWithInput(testSecretWord) {
        controller.start(testSecretWord)
      }

      winningOutput should include("Willkommen zu Wordle!")
      winningOutput should include("Gewonnen")





      val testSecretWord2 = "B"
      val losingOutput = captureConsoleOutputWithInput(testSecretWord2) {
        controller.start(testSecretWord2)
      }
      losingOutput should include("Willkommen zu Wordle!")
      losingOutput should include("Verloren")

    }
  }


  //Dient um Konsole Ausgabe zu lesen und Input zu simulieren
  def captureConsoleOutputWithInput(input: String)(block: => Unit): String = {
    val outputBuffer = new java.io.ByteArrayOutputStream
    val inputBuffer = new java.io.ByteArrayInputStream(input.getBytes)
    Console.withOut(outputBuffer) {
      Console.withIn(inputBuffer) {
        block
      }
    }
    outputBuffer.toString

}}



/*import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._



class wordlespec extends AnyWordSpec {

  "Wordle" should {


    val array1Buchstab: Array[String] = Array("A", "B", "C", "D")
    val loesungsword = "B"

      "have an array and a solution word" in {
        array1Buchstab.mkString(", ") should be ("A, B, C, D")  //.mkString(", ") --> "A, B, C, D"
        loesungsword should be("B")
    }

    }




}
*/