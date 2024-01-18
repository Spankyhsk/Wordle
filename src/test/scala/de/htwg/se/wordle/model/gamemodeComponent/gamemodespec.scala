package de.htwg.se.wordle.model.gamemodeComponnent

import org.scalatest.wordspec.AnyWordSpec

class GamemodeSpec extends AnyWordSpec {

  "Gamemode1" should {
    val wordObject = new Word() //..
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

  "Gamemode2" should {
    val wordObject = new Word() //...
    val gamemode2Instance = gamemode2(wordObject)

    "correctly get the target word" in {
      assert(gamemode2Instance.getTargetword().size == 2)
    }

    "correctly get the limit" in {
      assert(gamemode2Instance.getLimit() == 7)
    }

    "correctly get the word list" in {
      assert(gamemode2Instance.getWordList().nonEmpty)
    }

    "allow setting a new target word" in {
      gamemode2Instance.setTargetWord(Map(1 -> "testWord1", 2 -> "testWord2"))
      assert(gamemode2Instance.getTargetword() == Map(1 -> "testWord1", 2 -> "testWord2"))
    }

    "allow setting a new limit" in {
      gamemode2Instance.setLimit(9)
      assert(gamemode2Instance.getLimit() == 9)
    }
  }

  "Gamemode3" should {
    val wordObject = new Word() //
    val gamemode3Instance = gamemode3(wordObject)

    "correctly get the target word" in {
      assert(gamemode3Instance.getTargetword().size == 4)
    }

    "correctly get the limit" in {
      assert(gamemode3Instance.getLimit() == 8)
    }

    "correctly get the word list" in {
      assert(gamemode3Instance.getWordList().nonEmpty)
    }

    "allow setting a new target word" in {
      gamemode3Instance.setTargetWord(Map(1 -> "word1", 2 -> "word2", 3 -> "word3", 4 -> "word4"))
      assert(gamemode3Instance.getTargetword() == Map(1 -> "word1", 2 -> "word2", 3 -> "word3", 4 -> "word4"))
    }

    "allow setting a new limit" in {
      gamemode3Instance.setLimit(11)
      assert(gamemode3Instance.getLimit() == 11)
    }
  }



}
