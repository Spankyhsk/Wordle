package de.htwg.se.wordle.model.gamemodeComponnent

trait GamemodeInterface {

  def getTargetword(): Map[Int, String]

  def getLimit(): Int

  def getWordList(): Array[String]

  def setTargetWord(targetWordMap: Map[Int, String]): Unit

  def setLimit(Limit: Int): Unit
  
  def toString():String
}
