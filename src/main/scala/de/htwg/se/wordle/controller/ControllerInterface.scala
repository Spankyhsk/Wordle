package de.htwg.se.wordle.controller
import de.htwg.se.wordle.util.Observable
import de.htwg.se.wordle.model.GameInterface
import scala.util.{Failure, Success, Try}

trait ControllerInterface extends Observable{
  def count(n:Int):Boolean
  def controllLength(n:Int):Boolean
  def controllRealWord(guess:String):Boolean
  def createGameboard():Unit
  def set(key:Int, feedback:Map[Int, String]):Unit
  def undo():Unit
  def evaluateGuess(guess:String):Map[Int, String]
  def toString():String
  def changeState(e:Int):Unit
  def getTargetword():Map[Int, String]
  def getLimit():Int
  def createwinningboard():Unit
  def areYouWinningSon(guess:String):Boolean
  def GuessTransform(guess:String):String

  def save(): Unit

  def load():Unit
  
  def setVersuche(zahl:Integer):Unit
  
  def getVersuche():Int

  def resetGameboard():Unit
}
