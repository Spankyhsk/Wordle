package de.htwg.se.wordle.model.gamemodeComponnent

import org.scalatest.wordspec.AnyWordSpec

class GamemodeSpec extends AnyWordSpec {

  "Gamemode1" should {
    val wordObject = new Word() // Ersetzen Sie dies durch eine tatsächliche Implementierung von Word
    val gamemode1Instance = gamemode1(wordObject)

    "correctly get the target word" in {
      assert(gamemode1Instance.getTargetword().nonEmpty)
    }

    "correctly get the limit" in {
      assert(gamemode1Instance.getLimit() == 6)
    }

    "correctly get the word list" in {
      assert(gamemode1Instance.getWordList().nonEmpty)
    }

    "allow setting a new target word" in {
      gamemode1Instance.setTargetWord(Map(1 -> "testWord"))
      assert(gamemode1Instance.getTargetword() == Map(1 -> "testWord"))
    }

    "allow setting a new limit" in {
      gamemode1Instance.setLimit(10)
      assert(gamemode1Instance.getLimit() == 10)
    }
  }

  // Ähnliche Tests für Gamemode2 und Gamemode3
}
