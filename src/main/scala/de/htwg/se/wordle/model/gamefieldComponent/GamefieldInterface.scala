package de.htwg.se.wordle.model.gamefieldComponent

trait GamefieldInterface {
  def set(key: Int, feedback: String): Unit

  def buildGamefield(n: Int, key: Int, value: String): Unit

  def toString: String
}
