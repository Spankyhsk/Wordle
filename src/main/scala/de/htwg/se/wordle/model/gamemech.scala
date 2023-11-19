package de.htwg.se.wordle.model

case class gamemech(){
  def count(n: Int, limit:Int): Boolean = {
    if (n < limit) true else false
  }

  def evaluateGuess(targetWord: String, guess: String): String = {
    val feedback = guess.zipWithIndex.map {
      case (g, i) if g == targetWord(i) => "\u001B[32m" + g + "\u001B[0m" //Tupel mit (buchstabe, index)
      case (g, _) if targetWord.contains(g) => "\u001B[33m" + g + "\u001B[0m"
      case (g, _) => g.toString
    }.mkString("")
    feedback
  }
  
}
