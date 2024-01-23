package de.htwg.se.wordle.model.gamemechComponent

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class SimpleGuessStrategySpec extends AnyWordSpec with Matchers {
  "A SimpleGuessStrategy" when {
    val guessStrategy = new SimpleGuessStrategy

    "evaluating a guess" should {
      "correctly evaluate each character in the guess" in {
        val targetWord = "apple"
        val guess = "alles"

        val evaluatedGuess = guessStrategy.evaluateGuess(targetWord, guess)
        evaluatedGuess should include ("\u001B[32ma\u001B[0m\u001B[33ml\u001B[0m\u001B[33ml\u001B[0m\u001B[33me\u001B[0ms"
        )

      }
    }




    "comparing the target word with a guess" should {
      "update the winning board correctly when a word is guessed" in {
        val targetWord = "apple"
        val guess = "apple"
        val n = 1
        val winningBoard = Map(1 -> false, 2 -> false)

        val updatedBoard = guessStrategy.compareTargetGuess(targetWord, guess, n, winningBoard)
        updatedBoard should contain(1 -> true)
        updatedBoard should contain(2 -> false)
      }

      "not update the winning board if the guess is incorrect" in {
        val targetWord = "apple"
        val guess = "wrong"
        val n = 1
        val winningBoard = Map(1 -> false, 2 -> false)

        val updatedBoard = guessStrategy.compareTargetGuess(targetWord, guess, n, winningBoard)
        updatedBoard should contain(1 -> false)
        updatedBoard should contain(2 -> false)
      }
    }
  }
}
