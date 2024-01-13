package de.htwg.se.wordle.model.gamefieldComponent

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameFieldSpec extends AnyWordSpec with Matchers {

  "A GameField" when {
    "empty" should {
      val emptyGameField = gamefield()

      "have an empty map" in {
        emptyGameField.getMap() should be(Map.empty)
      }

      "produce an empty string representation" in {
        emptyGameField.toString should be("")
      }
    }

    "after setting a value" should {
      val gameField = gamefield()
      gameField.set(1, "Test")

      "have the value in the map" in {
        gameField.getMap() should be(Map(1 -> "Test"))
      }

      "produce the correct string representation" in {
        gameField.toString should be("Test")
      }
    }

    "after building the gamefield" should {
      val gameField = gamefield()
      gameField.buildGamefield(3, 1, "Word")

      "have the correct map" in {
        gameField.getMap() should be(Map(1 -> "Word", 2 -> "Word", 3 -> "Word"))
      }

      "produce the correct string representation" in {
        gameField.toString should be("Word\nWord\nWord")
      }
    }

    "after setting a value and building the gamefield" should {
      val gameField = gamefield()
      gameField.set(1, "Test")
      gameField.buildGamefield(3, 2, "Word")

      "have the correct map" in {
        gameField.getMap() should be(Map(1 -> "Test", 2 -> "Word", 3 -> "Word"))
      }

      "produce the correct string representation" in {
        gameField.toString should be("Test\nWord\nWord")
      }
    }

    "after being reset" should {
      val gameField = gamefield()
      gameField.set(1, "Test")
      gameField.reset()

      "have an empty map" in {
        gameField.getMap() should be(Map.empty)
      }

      "produce an empty string representation" in {
        gameField.toString should be("")
      }
    }

    "after setting multiple values" should {
      val gameField = gamefield()
      gameField.set(1, "Test")
      gameField.set(2, "Word")

      "have the correct values in the map" in {
        gameField.getMap() should be(Map(1 -> "Test", 2 -> "Word"))
      }

      "produce the correct string representation" in {
        gameField.toString should be("Test\nWord")
      }
    }


  }
}







