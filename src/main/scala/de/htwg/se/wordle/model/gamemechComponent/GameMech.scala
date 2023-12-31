package de.htwg.se.wordle.model.gamemechComponent

import scala.util.Random


  // Abstrakte Strategie für das Erraten von Wörtern
  

  // Konkrete Implementierung der Strategie


  // Klasse für den Spielmechanismus mit Strategie
  case class GameMech(guessStrategy: GuessStrategy = new SimpleGuessStrategy)extends gamemechInterface {
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

    def resetWinningBoard(size: Int): Unit = {
      winningBoard = (1 to size).map(_ -> false).toMap
    }

    def compareTargetGuess(n: Int, targetWord: Map[Int, String], guess: String): Unit = {
      if (winningBoard.contains(n) && !getWin(n)) {
        val updatedBoard = guessStrategy.compareTargetGuess(targetWord(n), guess, n, winningBoard)
        winningBoard ++= updatedBoard
      }
      if (n < winningBoard.size) compareTargetGuess(n + 1, targetWord, guess)
    }

    def evaluateGuess(targetWord: String, guess: String): String = {
      guessStrategy.evaluateGuess(targetWord, guess)
    }
  }

  
  

