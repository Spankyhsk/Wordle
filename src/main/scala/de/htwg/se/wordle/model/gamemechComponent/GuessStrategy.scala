package de.htwg.se.wordle.model.gamemechComponent
// Abstrakte Strategie für das Erraten von Wörtern
trait GuessStrategy {
  def compareTargetGuess(targetWord: String, guess: String, n: Int, winningBoard: Map[Int, Boolean]): Map[Int, Boolean]

  def evaluateGuess(targetWord: String, guess: String): String

}
