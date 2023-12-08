package de.htwg.se.wordle.model

import de.htwg.se.wordle.model.gamemechComponent.gamemech.GameMech
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
class gamemechspec extends AnyWordSpec with Matchers{
  "Gamemech" when{
    "SimpleGuessStrategy" should{
      /*"evaluate guess and display feedback (green letter)" in {
        val GuessStrategy = new SimpleGuessStrategy()
        val targetWord = "abc"
        val guess = "abc"
        val feedback = GuessStrategy.evaluateGuess(targetWord, guess)

        feedback should be("\u001B[32ma\u001B[0m\u001B[32mb\u001B[0m\u001B[32mc\u001B[0m")
      }

      "evaluate guess handle incorrect guess" in {
        val GuessStrategy = new SimpleGuessStrategy()
        val targetWord = controller.targetword
        val guess = "xyzer"
        val feedback = GuessStrategy.evaluateGuess(targetWord, guess)

        feedback.trim should be("xyzer")
      }*/

    }
    
    "GameMech" should{
      "count checked if n smaller as limit" in{
        val gamemech = new GameMech()
        val check1 = gamemech.count(1, 2)
        val check2 = gamemech.count(2,1)

        check1 should be(true)
        check2 should be(false)
      }
      "controllLength checked that n have the same length as Wordlength" in{
        val gamemech = new GameMech()
        val check1 = gamemech.controllLength(2,2)
        val check2 = gamemech.controllLength(2,1)

        check1 should be(true)
        check2 should be(false)
      }
      "controllRealWord checked that guess is in wordList" in{
        val gamemech = new GameMech()
        val wordList = Array("Apfel", "Birne")

        val check1 = gamemech.controllRealWord("Apfel", wordList)
        val check2 = gamemech.controllRealWord("Karotte", wordList)

        check1 should be(true)
        check2 should be(false)
      }

      "buildwinningboard build a Map with false as value with n Keys" in{
        val gamemech = new GameMech()
        gamemech.buildwinningboard(1,1)

        gamemech.winningBoard.size should be(1)
      }

      "setWin set true by key" in{
        val gamemech = new GameMech()
        gamemech.buildwinningboard(1,1)
        gamemech.setWin(1)

        gamemech.getWin(1) should be(true)
      }

      "getWin give value from key"in{
        val gamemech = new GameMech()
        gamemech.buildwinningboard(1, 1)

        gamemech.getWin(1) should be(false)
      }

      "areYouWinningSon checked if all values in map are true"in{
        val gamemech = new GameMech()
        gamemech.buildwinningboard(1, 1)
        val check1 = gamemech.areYouWinningSon()
        gamemech.setWin(1)
        val check2 = gamemech.areYouWinningSon()

        check1 should be(false)
        check2 should be(true)
      }

      "GuessTransform set all Low Letter to Big Letter in String"in{
        val gamemech = new GameMech()
        val testo = gamemech.GuessTransform("dc")

        testo should be("DC")
      }

      "compareTargetGuess cheked if Targetword and guess"in{
        val gamemech = new GameMech()
        val targetWord = Map(
          1 -> "Apfel",
          2 -> "Birne"
        )

      }

      "evaluateGuess make a evaluateGuess with targetwort and guess and give back a feedback"in{
        val gamemech = new GameMech()
        val testo = gamemech.evaluateGuess("gg", "tz")

        testo should be("tz")
      }


    }
  }

}
