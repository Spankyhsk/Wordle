package de.htwg.se.wordle.model

import de.htwg.se.wordle.model.gamemechComponent.*
import de.htwg.se.wordle.model.gamefieldComponent.*
import de.htwg.se.wordle.model.gamemodeComponnent.*


case class Game(mech:gamemechInterface, board:GamefieldInterface[GamefieldInterface[String]],var mode:GamemodeInterface)extends GameInterface{
  def this() = this(new GameMech, new gameboard(), gamemode(1))
  def getGamemech(): gamemechInterface ={
    mech
  }

  def getGamefield(): GamefieldInterface[GamefieldInterface[String]] ={
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
    //gameboard().map = Map.empty[Int, GamefieldInterface[String]]
    board.buildGameboard(mode.getTargetword().size, 1)
    createGamefieldR(1)
  }

  def createGamefieldR(n: Int): Unit = {
    board.getMap()(n).buildGamefield(getLimit(), 1, s"?" * getTargetword()(1).length)
    if (n < board.getMap().size) createGamefieldR(n + 1)
  }

  def evaluateGuess(guess: String): Map[Int, String] = {
    val keys: List[Int] = getTargetword().keys.toList.sorted // Stellen Sie sicher, dass die Schlüssel sortiert sind
    val feedback: Map[Int, String] = keys.map(key => key -> mech.evaluateGuess(getTargetword()(key), guess)).toMap
    feedback
  }


  override def toString(): String = {
    board.toString
  }

  def changeState(e: Int): Unit = {
    mode = gamemode(e)
    mech.resetWinningBoard(mode.getTargetword().size)
    resetGameboard()
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
    //GameMech().winningBoard = Map.empty[Int, Boolean]
    mech.buildwinningboard(board.getMap().size, 1)
  }

  def areYouWinningSon(guess: String): Boolean = {
    mech.compareTargetGuess(1, getTargetword(), guess)
    mech.areYouWinningSon()
  }

  def GuessTransform(guess: String): String = {
    mech.GuessTransform(guess)
  }

  def resetGameboard(): Unit = {
    board.reset()
    createGameboard()
    createwinningboard()
  }
  
  def setWinningboard(wBoard: Map[Int, Boolean]) = {
    mech.setWinningboard(wBoard)
  }

  def setN(zahl: Integer): Unit={
    mech.setN(zahl)
  }
  def getN():Int={
    mech.getN()
  }

  def setTargetWord(targetWordMap: Map[Int, String]): Unit={
    mode.setTargetWord(targetWordMap)
  }

  def setLimit(Limit: Int): Unit={
    mode.setLimit(Limit)
  }

  def setMap(boardmap:Map[Int, Map[Int, String]]):Unit={
    board.setMap(boardmap)
  }


}



object Game:
  def apply(kind:String)={
    kind match{
      case "norm" => new Game(new GameMech(), new gameboard() , gamemode(1))
    }
  }
