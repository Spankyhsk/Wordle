
package de.htwg.se.wordle.controller
import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.model.attempt
import de.htwg.se.wordle.model.gamefield
import de.htwg.se.wordle.util.Observable

case class controll (gm: gamemode.State)extends Observable {


  val limit = gm.getLimit()
  val targetword = gm.getTargetword()
  val gamefield = new gamefield()

  def count(n:Int):Boolean={

    if(n < limit) true else false

  }
  def createGamefield():Unit ={
    gamefield.buildGamefield(limit, 1, "_"*targetword.size)
    notifyObservers
  }

  def set(key:Int,feedback:String):Unit={
    gamefield.set(key, feedback)
    notifyObservers
  }


  def evaluateGuess(targetWord: String, guess: String): String = {
    val feedback = guess.zipWithIndex.map {
      case (g, i) if g == targetWord(i) => "\u001B[32m" + g + "\u001B[0m" //Tupel mit (buchstabe, index)
      case (g, _) if targetWord.contains(g) => "\u001B[33m" + g + "\u001B[0m"
      case (g, _) => g.toString
    }.mkString("")

    feedback
  }

  override def toString: String = {gamefield.toString}

  def changeState(e:Int):Unit={
    gm.handle(e)
  }


}
