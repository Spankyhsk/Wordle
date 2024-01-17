package de.htwg.se.wordle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.wordle.model.gamemechComponent.*
import de.htwg.se.wordle.model.gamefieldComponent.*
import de.htwg.se.wordle.model.gamemodeComponnent.*

class Gamespec extends AnyWordSpec with Matchers {

  "Game" when{
    "Game" should{
      "getGamemech give the gamemechinterface out" in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val mech = game.getGamemech()
        mech should be (game.mech)
      }

      "count checked if Limit is reached"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        game.setN(1)
        game.setLimit(1)
        val check1 = game.count()

        game.setLimit(2)
        val check2 = game.count()

        check1 should be(false)
        check2 should be(true)

      }

      "controllLength checked if length of guess equals lenght of target" in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val check1 = game.controllLength(5)
        val check2 = game.controllLength(4)

        check1 should be(true)
        check2 should be(false)
      }

      "evaluteGuess transform a guess to feedback map"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))


      }

      "createwinningboard build a winningboard"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        
      }

      "areYouWinningSon checked if you won"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
      }

      "GuessTransform transform small chars of guess to big chars"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        val check = game.GuessTransform("hallo")

        check should be("HALLO")
      }

      "setWinningboard update a Winningboard in mech"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        val map:Map[Int, Boolean] = Map(1 -> true)

        game.setWinningboard(map)
        game.mech.getWinningboard() should be(map)
      }

      "setN upgrade number of attempt"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        game.setN(2)
        val check1 = game.getN()

        check1 should be(2)
      }

      "getN give number of attempt"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        game.setN(2)
        val check1 = game.getN()

        check1 should be(2)
      }

      "getGameField give the gameboard out"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val board = game.getGamefield()
        board should be(game.board)
      }

      "createGameboard build a gameboard"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        game.createGameboard()

        val check = game.toString

        check should be("?????\n?????\n?????\n?????\n?????\n?????")
      }

      "createGamefieldR build one or many Gamefields"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
      }

      "toString give a String of gameboard out"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        game.createGameboard()

        val check = game.toString

        check should be("?????\n?????\n?????\n?????\n?????\n?????")
      }

      "resetGameboard reset the gameboard"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
      }

      "setMap update the gameboard"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
      }

      "getGamemode give the Gamemode out"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        val check = game.getGamemode()

        check should be(game.mode)
      }

      "changestate change the gamemode"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        game.changeState(2)

        val check = game.getTargetword().size

        check should be(2)
      }

      "getTargetword give the solution out"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val map:Map[Int, String] =Map(1->"GABEL")
        game.setTargetWord(map)

        val check = game.getTargetword()

        check should be(map)
      }

      "getLimit give the limit out"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        game.setLimit(1)

        val check = game.getLimit()

        check should be(1)
      }

      "setTargetWord upgrade Targetword"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))

        val map: Map[Int, String] = Map(1 -> "GABEL")
        game.setTargetWord(map)

        val check = game.getTargetword()

        check should be(map)
      }

      "setLimit give the Limit out"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        game.setLimit(1)

        val check = game.getLimit()

        check should be(1)
      }

      "TargetwordToString give a String of Targetword out"in{
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val map:Map[Int, String] =Map(1->"GABEL")

        game.setTargetWord(map)

        val check = game.TargetwordToString()

        check should be("Wort1: GABEL")
      }
    }
  }

}
