package de.htwg.se.wordle.model

import de.htwg.se.wordle.model.gamemechComponent.gamemechInterface
import de.htwg.se.wordle.model.gamefieldComponent.GamefieldInterface
import de.htwg.se.wordle.model.gamemodeComponnent.GamemodeInterface
trait GameInterface {
  def getGamemech():gamemechInterface
  def getGamefield():GamefieldInterface[GamefieldInterface[String]]
  def getGamemode():GamemodeInterface

  def count(): Boolean

  def controllLength(n: Int): Boolean

  def controllRealWord(guess: String): Boolean

  def createGameboard(): Unit

  def createGamefieldR(n: Int): Unit

  def evaluateGuess(guess: String): Map[Int, String]

  def toString(): String

  def changeState(e: Int): Unit

  def getTargetword(): Map[Int, String]

  def getLimit(): Int

  def createwinningboard(): Unit

  def areYouWinningSon(guess: String): Boolean
  def GuessTransform(guess:String):String

  def resetGameboard(): Unit

  def setWinningboard(wBoard: Map[Int, Boolean]):Unit
  def setN(zahl: Integer): Unit

  def getN():Int

  def setMap(boardmap:Map[Int, Map[Int, String]]):Unit

  def setTargetWord(targetWordMap: Map[Int, String]): Unit
  def setLimit(Limit: Int): Unit

  

}
