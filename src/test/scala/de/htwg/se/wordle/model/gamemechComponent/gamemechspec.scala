package de.htwg.se.wordle.model.gamemechComponent

import de.htwg.se.wordle.model.gamemechComponent.GameMech
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
class gamemechspec extends AnyWordSpec with Matchers{
  // Mock-Implementierung der GuessStrategy
  class MockGuessStrategy extends GuessStrategy {
    override def compareTargetGuess(target: String, guess: String, key: Int, winningBoard: Map[Int, Boolean]): Map[Int, Boolean] = {
      // Implementieren Sie die Logik, die für Ihre Tests erforderlich ist
      // Zum Beispiel: Wenn das geratene Wort mit dem Zielwort übereinstimmt, setzen Sie den Schlüssel auf `true`
      if (target.equalsIgnoreCase(guess)) {
        winningBoard.updated(key, true)
      } else {
        winningBoard
      }
    }

    override def evaluateGuess(targetWord: String, guess: String): String = {
      // Implementieren Sie eine einfache Logik oder geben Sie einen festen Wert zurück
      // Dieser Teil kann angepasst werden, um verschiedene Testszenarien zu unterstützen
      "some feedback"
    }
  }


  "Gamemech" when{
    "SimpleGuessStrategy" should{


    }
    
    "GameMech" should{

      "controllLength checked that n have the same length as Wordlength" in{
        val gamemech = new GameMech()
        val check1 = gamemech.controllLength(2,2)
        val check2 = gamemech.controllLength(2,1)

        check1 should be(true)
        check2 should be(false)
      }
      "controllRealWord checked that guess is in wordList" in{
        val gamemech = new GameMech()

        val check1 = gamemech.controllRealWord("Apfel")
        val check2 = gamemech.controllRealWord("2cool4school")

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

      "build a winning board recursively" in {
        val gamemech = new GameMech()
        gamemech.buildwinningboard(3, 1)

        gamemech.getWinningboard() should be(Map(1 -> false, 2 -> false, 3 -> false))
      }

      "reset the winning board with a given size" in {
        val gamemech = new GameMech()
        gamemech.resetWinningBoard(2)

        gamemech.getWinningboard() should be(Map(1 -> false, 2 -> false))
      }

      "compare target and guess when the key is in the winning board and not already won" in {
        val gamemech = new GameMech(new MockGuessStrategy())
        gamemech.buildwinningboard(2, 1)
        val targetWord = Map(1 -> "apple", 2 -> "banana")
        val guess = "apple"

        gamemech.compareTargetGuess(1, targetWord, guess)

        // Überprüfen Sie, ob das winningBoard entsprechend aktualisiert wurde
        gamemech.getWinningboard()(1) should be(true)
      }

      "not update the winning board for keys beyond its size" in {
        val gamemech = new GameMech(new MockGuessStrategy())
        gamemech.buildwinningboard(2, 1)
        val targetWord = Map(1 -> "apple", 2 -> "banana")
        val guess = "apple"

        gamemech.compareTargetGuess(3, targetWord, guess)

        // Das winningBoard sollte keine Änderungen für Schlüssel außerhalb seiner Größe haben
        gamemech.getWinningboard().size should be(2)
      }


    }
  }

}
