
package de.htwg.se.wordle.controller
import de.htwg.se.wordle.util.Observable
import de.htwg.se.wordle.util.Command
import de.htwg.se.wordle.model.GameInterface

import scala.util.{Failure, Success, Try}
import de.htwg.se.wordle.util.UndoManager

case class controll (game:GameInterface)extends Observable {

  var gamemode = game.getGamemode()
  val gamemech = game.getGamemech()
  val gameboard = game.getGamefield()
  private val undoManager = new UndoManager

  
  def count(n: Int): Boolean = {
    if (gamemech.count(n, getLimit())) true else false
  }

  def controllLength(n: Int): Boolean = {
    if (gamemech.controllLength(n, gamemode.getTargetword()(1).length)) true else false
  }

  def controllRealWord(guess: String): Boolean = {
    if (gamemech.controllRealWord(guess, gamemode.getWordList())) true else false
  }

  def createGameboard(): Unit = {
    gameboard.map = Map.empty[Int, gamefieldComponent.gamefield.Component]
    gameboard.buildGameboard(getTargetword().size, 1)
    createGamefieldR(1)
    notifyObservers
  }


  def createGamefieldR(n: Int): Unit = {
    gameboard.getChilderen(n).buildGamefield(getLimit(), 1, s"_" * getTargetword()(1).length)
    if (n < gameboard.map.size) createGamefieldR(n + 1)
  }

  def set(key: Int, feedback: Map[Int, String]): Unit = {
    undoManager.doStep(new SetCommand(key, feedback, this))
    notifyObservers
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

 

  def evaluateGuess(guess: String): Map[Int, String] = {
    val keys: List[Int] = getTargetword().keys.toList
    val feedback: Map[Int, String] = keys.map(key => key -> gamemech.evaluateGuess(getTargetword()(key), guess)).toMap
    feedback
  }

  override def toString: String = {
    gameboard.toString
  }

  def changeState(e: Int): Unit = {
    gamemode = gm.handle(e)

  }

  def getTargetword(): Map[Int, String] = {
    val targetword = gamemode.getTargetword()
    targetword
  }

  def getLimit(): Int = {
    val limit = gamemode.getLimit()
    limit
  }

  def createwinningboard(): Unit = {
    gamemech.winningBoard = Map.empty[Int, Boolean]
    gamemech.buildwinningboard(gameboard.map.size, 1)
  }

  def areYouWinningSon(guess: String): Boolean = {
    gamemech.compareTargetGuess(1, getTargetword(), guess)
    gamemech.areYouWinningSon()
  }

  def GuessTransform(guess: String): String = {
    gamemech.GuessTransform(guess)
  }

  


}
