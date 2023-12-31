
package de.htwg.se.wordle.controller
import de.htwg.se.wordle.util.Observable
import de.htwg.se.wordle.model.GameInterface
import de.htwg.se.wordle.model.Game
import de.htwg.se.wordle.util.Event

import scala.util.{Failure, Success, Try}
import de.htwg.se.wordle.util.UndoManager

case class controll (game:GameInterface)extends ControllerInterface with Observable {

  var gamemode = game.getGamemode()
  val gamemech = game.getGamemech()
  val gameboard = game.getGamefield()
  private val undoManager = new UndoManager

  
  def count(n: Int): Boolean = {
    game.count(n)
  }

  def controllLength(n: Int): Boolean = {
    game.controllLength(n)
  }

  def controllRealWord(guess: String): Boolean = {
    game.controllRealWord(guess)
  }

  def createGameboard(): Unit = {
    game.createGameboard()
    notifyObservers(Event.Move)
  }


  

  def set(key: Int, feedback: Map[Int, String]): Unit = {
    undoManager.doStep(new SetCommand(key, feedback, this))
    notifyObservers(Event.Move)
  }

  def undo(): Unit = {
    undoManager.undoStep
  }

 

  def evaluateGuess(guess: String): Map[Int, String] = {
    game.evaluateGuess(guess)
  }

  override def toString: String = {
    game.toString
  }

  def changeState(e: Int): Unit = {
    game.resetGameboard() // Spielbrett zurücksetzen
    game.changeState(e)
    createGameboard() // Neues Spielbrett initialisieren
    notifyObservers(Event.Move)
  }


  def getTargetword(): Map[Int, String] = {
    game.getTargetword()
  }

  def getLimit(): Int = {
    game.getLimit()
  }

  def createwinningboard(): Unit = {
    game.createwinningboard()
  }

  def areYouWinningSon(guess: String): Boolean = {
    game.areYouWinningSon(guess)
  }

  def GuessTransform(guess: String): String = {
    game.GuessTransform(guess)
  }
  
}

object controll:
  def apply(kind:String):controll ={
    kind match {
      case "norm" => controll(Game("norm"))
    }
  }
