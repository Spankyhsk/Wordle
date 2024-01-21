package de.htwg.se.wordle.model.gamefieldComponent

trait GamefieldInterface[T] {
  def set(key: Int, feedback: String): Unit
  def buildGamefield(n: Int, key: Int, value: String): Unit
  def buildGameboard(n: Int, key: Int): Unit
  def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit
  def getMap():Map[Int, T]
  def reset(): Unit
  def setMap(boardmap:Map[Int, Map[Int, String]]):Unit
  def toString: String
}
