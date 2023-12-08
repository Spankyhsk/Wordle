package de.htwg.se.wordle.model

import de.htwg.se.wordle.model.gamemechComponent.*
import de.htwg.se.wordle.model.gamefieldComponent.*
import de.htwg.se.wordle.model.gamemodeComponnent.*


case class Game(mech:gamemechInterface, board:GamefieldInterface,var mode:GamemodeInterface)extends GameInterface{
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
    gameboard().map = Map.empty[Int, GamefieldInterface]
    gameboard().buildGameboard(getTargetword().size, 1)
    createGamefieldR(1)
  }

  def createGamefieldR(n: Int): Unit = {
    gameboard().getChilderen(n).buildGamefield(getLimit(), 1, s"_" * getTargetword()(1).length)
    if (n < gameboard().map.size) createGamefieldR(n + 1)
  }

  def evaluateGuess(guess: String): Map[Int, String] = {
    val keys: List[Int] = getTargetword().keys.toList
    val feedback: Map[Int, String] = keys.map(key => key -> mech.evaluateGuess(getTargetword()(key), guess)).toMap
    feedback
  }

  override def toString(): String = {
    board.toString
  }

  def changeState(e: Int): Unit = {
    mode = gamemode(e)
  }

  def getTargetword(): Map[Int, String] = {
    val targetword = mode.getTargetword()
    targetword
  }

  def getLimit(): Int = {
    val limit = mode.getLimit()
    limit
  }

  def createwinningboard(): Unit = {
    GameMech().winningBoard = Map.empty[Int, Boolean]
    mech.buildwinningboard(gameboard().map.size, 1)
  }

  def areYouWinningSon(guess: String): Boolean = {
    mech.compareTargetGuess(1, getTargetword(), guess)
    mech.areYouWinningSon()
  }

  def GuessTransform(guess: String): String = {
    mech.GuessTransform(guess)
  }
}
