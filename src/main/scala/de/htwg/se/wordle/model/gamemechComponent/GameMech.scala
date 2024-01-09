package de.htwg.se.wordle.model.gamemechComponent

import java.beans.Encoder
import scala.util.Random


  // Abstrakte Strategie für das Erraten von Wörtern
  

  // Konkrete Implementierung der Strategie


  // Klasse für den Spielmechanismus mit Strategie
  case class GameMech(guessStrategy: GuessStrategy = new SimpleGuessStrategy)extends gamemechInterface {
    var winningBoard = Map.empty[Int, Boolean]
    var n = 1

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
    
    def getN(): Integer = {
      n
    }

    def setN(zahl: Integer): Unit = {
      n = zahl
    }
    
    def getWinningboard():Map[Int, Boolean]={
      winningBoard
    }
    
    def setWinningboard(wBoard:Map[Int, Boolean])={
      winningBoard = wBoard
    }
    
    
  }

object Gamemech{
  import play.api.libs.json._
  implicit val GameMechFormat:Writes[GameMech] = new Writes[GameMech] {
    
    def writes(mech: gamemechInterface): JsValue = Json.obj(
      "winningboard" -> mech.getWinningboard().toString(),
      "Versuch" -> mech.getN().toString
    )
  }
  
  /*
  implicit val mapWrites:Writes[Map[Int, Boolean]] = new Writes[Map[Int, Boolean]] {
    override def writes(map: Map[Int, Boolean]): JsValue = {
      Json.obj(map.map{case (key, value)=> key.toString -> Json.toJsFieldJsValueWrapper(JsBoolean(value))}.toSeq: _*)
    }
  }*/
}


  

