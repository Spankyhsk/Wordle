package de.htwg.se.wordle.model

import scala.util.Random

object gamemech {
  // Abstrakte Strategie für das Erraten von Wörtern
  trait GuessStrategy {
    def compareTargetGuess(targetWord: String, guess: String, n: Int, winningBoard: Map[Int, Boolean]): Map[Int, Boolean]

    def evaluateGuess(targetWord: String, guess: String): String
  }

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

  // Klasse für den Spielmechanismus mit Strategie
  case class GameMech(guessStrategy: GuessStrategy = new SimpleGuessStrategy) {
    var winningBoard = Map.empty[Int, Boolean]

    def count(n: Int, limit: Int): Boolean = {
      if (n < limit) true else false
    }

    def controllLength(n: Int, wordLength: Int): Boolean = {
      if (n == wordLength) true else false
    }

    def controllRealWord(guess: String, wordList: Array[String]): Boolean = {
      if (wordList.contains(guess)) true else false
    }

    def buildwinningboard(n: Int, key: Int): Unit = {
      winningBoard += (key -> false)
      if (key < n) buildwinningboard(n, key + 1)
    }

    def setWin(key: Int): Unit = {
      winningBoard = winningBoard + (key -> true)
    }

    def getWin(key: Int): Boolean = {
      winningBoard(key)
    }

    def areYouWinningSon(): Boolean = {
      val allValuesEqualTrue: Boolean = winningBoard.values.forall(_ == true)
      allValuesEqualTrue
    }

    def GuessTransform(guess: String): String = {
      guess.toUpperCase
    }


    def compareTargetGuess(n: Int, targetWord: Map[Int, String], guess: String): Unit = {
      if (!getWin(n)) {
        val updatedBoard = guessStrategy.compareTargetGuess(targetWord(n), guess, n, winningBoard)
        winningBoard ++= updatedBoard
      }
      if (n < winningBoard.size) compareTargetGuess(n + 1, targetWord, guess)
    }

    def evaluateGuess(targetWord: String, guess: String): String = {
      guessStrategy.evaluateGuess(targetWord, guess)
    }
  }
}
  
  

