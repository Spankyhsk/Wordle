package de.htwg.se.wordle.model

import de.htwg.se.wordle.model.gamemechComponent.*
import de.htwg.se.wordle.model.gamefieldComponent.*
import de.htwg.se.wordle.model.gamemodeComponnent.*


case class Game(mech:gamemechInterface, board:GamefieldInterface[GamefieldInterface[String]],var mode:GamemodeInterface)extends GameInterface{
  def this() = this(new GameMech, new gameboard(), gamemode(1))

  //===========================================================================

              //!!!Mech!!!

  //===========================================================================

  def getGamemech(): gamemechInterface = {
    mech
  }

  def count(): Boolean = {
    mech.count(mode.getLimit())
  }

  def controllLength(n: Int): Boolean = {
    mech.controllLength(n, mode.getTargetword()(1).length())
  }

  def controllRealWord(guess: String): Boolean = {
    mech.controllRealWord(guess)
  }

  def evaluateGuess(guess: String): Map[Int, String] = {
    val keys: List[Int] = getTargetword().keys.toList.sorted // Stellen Sie sicher, dass die Schlüssel sortiert sind
    val feedback: Map[Int, String] = keys.map(key => key -> mech.evaluateGuess(getTargetword()(key), guess)).toMap
    feedback
  }

  def createwinningboard(): Unit = {
    mech.buildwinningboard(board.getMap().size, 1)
  }

  def areYouWinningSon(guess: String): Boolean = {
    mech.compareTargetGuess(1, getTargetword(), guess) //??
    mech.areYouWinningSon()
  }

  def GuessTransform(guess: String): String = {
    mech.GuessTransform(guess)
  }

  def setWinningboard(wBoard: Map[Int, Boolean]) = {
    mech.setWinningboard(wBoard)
  }

  def setN(zahl: Integer): Unit = {
    mech.setN(zahl)
  }

  def getN(): Int = {
    mech.getN()
  }

  //===========================================================================

            //!!!Board!!!

  //===========================================================================

  def getGamefield(): GamefieldInterface[GamefieldInterface[String]] = {
    board
  }

  def createGameboard(): Unit = {
    board.buildGameboard(mode.getTargetword().size, 1)
    createGamefieldR(1)
  }

  def createGamefieldR(n: Int): Unit = {
    board.getMap()(n).buildGamefield(getLimit(), 1, s"?" * getTargetword()(1).length)
    if (n < board.getMap().size) createGamefieldR(n + 1)
  }

  override def toString(): String = {
    board.toString
  }

  def resetGameboard(): Unit = {
    board.reset()
  }

  def setMap(boardmap: Map[Int, Map[Int, String]]): Unit = {
    board.setMap(boardmap)
  }

  //===========================================================================

            //!!!Mode!!!

  //===========================================================================

  def getGamemode(): GamemodeInterface ={
    mode
  }

  def changeState(e: Int): Unit = {
    mode = gamemode(e)
    mech.resetWinningBoard(mode.getTargetword().size)
    resetGameboard()
  }

  def getTargetword(): Map[Int, String] = {
    mode.getTargetword()
  }

  def getLimit(): Int = {
    mode.getLimit()
  }

  def setTargetWord(targetWordMap: Map[Int, String]): Unit={
    mode.setTargetWord(targetWordMap)
  }

  def setLimit(Limit: Int): Unit={
    mode.setLimit(Limit)
  }
  def TargetwordToString():String={
    mode.toString()
  }
  

}

object Game:
  def apply(kind:String)={
    kind match{
      case "norm" => new Game(new GameMech(), new gameboard() , gamemode(1))
    }
  }
