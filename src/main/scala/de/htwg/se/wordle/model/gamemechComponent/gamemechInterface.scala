package de.htwg.se.wordle.model.gamemechComponent

trait gamemechInterface {
  def count( limit:Int):Boolean
  def controllLength(n:Int, wordLength:Int):Boolean
  def controllRealWord(guess:String):Boolean
  def buildwinningboard(n:Int, key:Int):Unit
  def setWin(key:Int):Unit
  def getWin(key:Int):Boolean
  def areYouWinningSon():Boolean
  def GuessTransform(guess:String):String
  def compareTargetGuess(n:Int, targetWord:Map[Int, String], guess:String):Unit
  def evaluateGuess(targetWord:String, guess:String):String

  def getN(): Int
  def setN(zahl: Integer): Unit
  def getWinningboard():Map[Int, Boolean]
  def setWinningboard(wBoard:Map[Int, Boolean]):Unit

  def resetWinningBoard(size: Int): Unit
}
