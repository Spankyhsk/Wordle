package de.htwg.se.wordle.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class gamemodespec extends AnyWordSpec with Matchers {

  "Gamemode" when {
    "in gamemode1" should {
      "return correct target word and limit" in {
        val state = gamemode.gamemode1()
        state.getTargetword() shouldBe Map(1 -> state.targetword(1))
        state.getLimit() shouldBe 3

      }
    }

    "in gamemode2" should {
      "return correct target word and limit" in {
        val state = gamemode.gamemode2()
        state.getTargetword() shouldBe Map(1 -> state.targetword(1), 2 -> state.targetword(2))
        state.getLimit() shouldBe 3

      }
    }

    "in gamemode3" should {
      "return correct target word and limit" in {
        val state = gamemode.gamemode3()
        state.getTargetword() shouldBe Map(1 -> state.targetword(1), 2 -> state.targetword(2), 3 -> state.targetword(3), 4 -> state.targetword(4))
        state.getLimit() shouldBe 3

      }
    }

    "handle method" should {
      "transition to the correct state" in {
        val state1 = gamemode.gamemode1()
        val state2 = gamemode.gamemode2()
        val state3 = gamemode.gamemode3()

        state1.handle(1) shouldBe state1
        state1.handle(2) shouldBe state2
        state1.handle(3) shouldBe state3

        state2.handle(1) shouldBe state1
        state2.handle(2) shouldBe state2
        state2.handle(3) shouldBe state3

        state3.handle(1) shouldBe state1
        state3.handle(2) shouldBe state2
        state3.handle(3) shouldBe state3
      }
    }

    "selectRandomWord method" should {
      "return a random word from the given array" in {
        val wordArray = Array("MMM", "EEE", "GGG", "AAA")
        val randomWord = gamemode.selectRandomWord(wordArray)
        wordArray should contain(randomWord)
      }
    }



    "getTargetword method" should {
      "return the correct target word for each game mode" in {
        val state1 = gamemode.gamemode1()
        val state2 = gamemode.gamemode2()
        val state3 = gamemode.gamemode3()

        state1.getTargetword() shouldBe Map(1 -> state1.targetword(1))
        state2.getTargetword() shouldBe Map(1 -> state2.targetword(1), 2 -> state2.targetword(2))
        state3.getTargetword() shouldBe Map(1 -> state3.targetword(1), 2 -> state3.targetword(2), 3 -> state3.targetword(3), 4 -> state3.targetword(4))
      }
    }

    "getLimit method" should {
      "return the correct limit for each game mode" in {
        gamemode.gamemode1().getLimit() shouldBe 3
        gamemode.gamemode2().getLimit() shouldBe 3
        gamemode.gamemode3().getLimit() shouldBe 3
      }
    }
  }
}
