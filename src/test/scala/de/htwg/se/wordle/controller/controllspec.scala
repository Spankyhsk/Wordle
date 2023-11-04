package de.htwg.se.wordle.controller

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.attempt
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayOutputStream


class controllspec extends AnyWordSpec {
  "The Controller" should{
    // Konsolenausgaben kommen in Puffer und wird als Zeichenkette ausgegebn
    def captureOutput(block: => Unit): String = {
      val outputStream = new ByteArrayOutputStream()
      Console.withOut(outputStream) {
        block
      }
      outputStream.toString
    }


    "Controller should initialize versuch and targetword from parameter" in{
      val attampt = new attempt("fisch", 1)
      val contoller = new controll(attampt)
      val limit = attampt.x
      val targetword = attampt.targetword
      limit should be(1)
      targetword should be("fisch")
    }

    "Count compares. number of attempt smaller as limit" in{
      val controller  = new controll(new attempt("fisch", 2))
      controller.count(1) should be(true)
    }

    "Count compares. number of attampt even as limit" in{
      val controller = new controll(new attempt("fisch", 2))
      controller.count(2) should be(false)
    }

    "evaluate guess and display feedback (green letter)" in {
      val controller = new controll(new attempt("fisch", 2))
      val targetWord = "abc"
      val guess = "abc"
      val feedback = captureOutput{
        controller.evaluateGuess(targetWord, guess)
      }
      feedback.trim should be("Dein Tipp: \u001B[32ma\u001B[0m\u001B[32mb\u001B[0m\u001B[32mc\u001B[0m")
    }

    "evaluate guess handle incorrect guess" in {
      val controller = new controll(new attempt("fisch", 2))
      val targetWord = "abc"
      val guess = "xyz"
      val feedback = captureOutput {
        controller.evaluateGuess(targetWord, guess)
      }
      feedback.trim should be("Dein Tipp: xyz")
    }
  }
}
