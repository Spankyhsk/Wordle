package de.htwg.se.wordle.model

import de.htwg.se.wordle.model.gamemechComponent.gamemechInterface
import de.htwg.se.wordle.model.gamefieldComponent.GamefieldInterface
import de.htwg.se.wordle.model.gamemodeComponnent.GamemodeInterface

case class Game(mech:gamemechInterface, board:GamefieldInterface, mode:GamemodeInterface)extends GameInterface{
  def getGamemech(): gamemechInterface ={
    mech
  }

  def getGamefield(): GamefieldInterface ={
    board
  }

  def getGamemode(): GamemodeInterface ={
    mode
  }

  def count(n: Int): Boolean ={
    mech.count(n,mode.getLimit())
  }

  def controllLength(n: Int): Boolean ={
    mech.controllLength(n,mode.getTargetword()(1).length())
  }

  def controllRealWord(guess: String): Boolean={
    mech.controllRealWord(guess, mode.getWordList())
  }

  def createGameboard(): Unit = {
    board.map = Map.empty[Int, gamefieldComponent.gamefield.Component]
    board.buildGameboard(getTargetword().size, 1)
    createGamefieldR(1)
  }

  def createGamefieldR(n: Int): Unit = {
    board.getChilderen(n).buildGamefield(getLimit(), 1, s"_" * getTargetword()(1).length)
    if (n < board.map.size) createGamefieldR(n + 1)
  }

  def evaluateGuess(guess: String): Map[Int, String]

  def toString(): String

  def changeState(e: Int): Unit

  def getTargetword(): Map[Int, String]

  def getLimit(): Int

  def createwinningboard(): Unit

  def areYouWinningSon(guess: String): Boolean

  def GuessTransform(guess: String): String
}
