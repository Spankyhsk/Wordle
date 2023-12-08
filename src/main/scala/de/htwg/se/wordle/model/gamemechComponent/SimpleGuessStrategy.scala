package de.htwg.se.wordle.model.gamemechComponent
// Konkrete Implementierung der Strategie
class SimpleGuessStrategy extends GuessStrategy {
  override def compareTargetGuess(targetWord: String, guess: String, n: Int, winningBoard: Map[Int, Boolean]): Map[Int, Boolean] = {
    val updatedBoard = winningBoard.collect {
      case (key, isWordGuessed) if key == n && !isWordGuessed && guess == targetWord =>
        key -> true
      case entry =>
        entry
    }

    updatedBoard
  }

  override def evaluateGuess(targetWord: String, guess: String): String = {
    guess.zipWithIndex.map {
      case (g, i) if g == targetWord(i) => "\u001B[32m" + g + "\u001B[0m"
      case (g, _) if targetWord.contains(g) => "\u001B[33m" + g + "\u001B[0m"
      case (g, _) => g.toString
    }.mkString("")
  }

}
