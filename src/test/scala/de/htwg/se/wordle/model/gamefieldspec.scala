
import de.htwg.se.wordle.model.gamefieldComponent.gamefield
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

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
        gameField.toString should be("Test\n")
      }
    }

    "after building the gamefield" should {
      val gameField = gamefield()
      gameField.buildGamefield(3, 1, "Word")

      "have the correct map" in {
        gameField.getMap() should be(Map(1 -> "Word", 2 -> "Word", 3 -> "Word"))
      }

      "produce the correct string representation" in {
        gameField.toString should be("Word\nWord\nWord\n")
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
        gameField.toString should be("Test\nWord\nWord\n")
      }
    }
  }
}







/*package de.htwg.se.wordle.model

import de.htwg.se.wordle.model.gamefieldComponent.gamefield
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class gamefieldspec extends AnyWordSpec with Matchers {

  "Gamefield" when {
    "using gamefield" should {
      "set feedback and build gamefield correctly" in {
        val gamefieldComponent = gamefield.gamefield()
        gamefieldComponent.set(1, "feedback1")
        gamefieldComponent.set(2, "feedback2")
        gamefieldComponent.buildGamefield(3, 3, "value")

        gamefieldComponent.toString shouldBe "feedback1\nfeedback2\nvalue\n"
      }


    }

    "using gameboard" should {
      "build gameboard correctly" in {
        val gameboardComponent = gamefield.gameboard()
        gameboardComponent.buildGameboard(3, 1)

        gameboardComponent.toString shouldBe "\n\n\n"
      }

      "get children from gameboard" in {
        val gameboardComponent = gamefield.gameboard()
        gameboardComponent.buildGameboard(3, 1)

        val children = gameboardComponent.getChilderen(1)
        children shouldBe a[gamefield.gamefield]
      }


    }
  }
}
*/