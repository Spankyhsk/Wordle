/*
package de.htwg.se.wordle.controller

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamefield
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

      val controller = new controll(new attempt("fisch", 1))
      val limit = controller.limit
      val targetword = controller.targetword
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
      val controller = new controll(new attempt("abc", 2))
      val targetWord = "abc"
      val guess = "abc"
      val feedback = controller.evaluateGuess(targetWord, guess)

      feedback should be("\u001B[32ma\u001B[0m\u001B[32mb\u001B[0m\u001B[32mc\u001B[0m")
    }

    "evaluate guess handle incorrect guess" in {
      val controller = new controll(new attempt("fisch", 2))
      val targetWord = controller.targetword
      val guess = "xyzer"
      val feedback = controller.evaluateGuess(targetWord, guess)

      feedback.trim should be("xyzer")
    }

    "createGamefield calls gamefield.buildGamefield" in{
      val controller = new controll(new attempt("fisch", 2))
      controller.createGamefield()
      controller.gamefield.map.size should be(2)

    }

    "set calls gamefield.set" in{
      val controller = new controll(new attempt("fisch", 2))
      controller.createGamefield()
      controller.set(1,"Tisch")
      controller.gamefield.map(1) should be("Tisch")

    }

    "toString calls gamefield.toString" in{
      val controller = new controll(new attempt("fisch", 2))
      controller.createGamefield()
      val s = controller.toString
      s should be("_____\n_____\n")
    }
  }
}
*/