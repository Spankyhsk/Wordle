package de.htwg.se.wordle.model.gamefieldComponent

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameBoardSpec extends AnyWordSpec with Matchers {

  "A GameBoard" when {
    "newly created" should {
      val gameBoard = gameboard()

      "have an empty map" in {
        gameBoard.getMap() should be(Map.empty)
      }

      "produce an empty string representation" in {
        gameBoard.toString should be("")
      }
    }

    "after setting up a gamefield map" should {
      val gameBoard = gameboard()
      gameBoard.setMap(Map(1 -> Map(1 -> "Word1", 2 -> "Word2"), 2 -> Map(1 -> "Word3")))

      "have the correct map values" in {
        val gameField1 = gamefield()
        gameField1.SetMapper(Map(1 -> "Word1", 2 -> "Word2"))

        val gameField2 = gamefield()
        gameField2.SetMapper(Map(1 -> "Word3"))

        val expectedMap = Map(
          1 -> gameField1,
          2 -> gameField2
        )
        gameBoard.getMap() should equal(expectedMap)
      }
    }


    "after building the gameboard" should {
      val gameBoard = gameboard()
      gameBoard.buildGameboard(2, 1)

      "have the correct number of gamefields" in {
        gameBoard.getMap().size should be(2)
      }

      "each gamefield should be empty" in {
        gameBoard.getMap().forall { case (_, gameField) => gameField.getMap().isEmpty } should be(true)
      }
    }

    "resetting the gameboard" should {
      val gameBoard = gameboard()
      gameBoard.buildGameboard(2, 1)
      gameBoard.reset()

      "have an empty map" in {
        gameBoard.getMap() should be(Map.empty)
      }
    }


  }
}
