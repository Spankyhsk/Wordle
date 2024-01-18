package de.htwg.se.wordle.controller
import de.htwg.se.wordle.util.Observable
import scala.util.{Failure, Success, Try}

trait ControllerInterface extends Observable{
  
  //==========================================================
  
              //!!!GAME!!!
  
  //==========================================================
  
  //----------------------------------------------------------
  
              //mech
  
  //----------------------------------------------------------

  def count():Boolean

  def controllLength(n:Int):Boolean

  def controllRealWord(guess:String):Boolean

  def evaluateGuess(guess:String):Map[Int, String]

  def GuessTransform(guess:String):String

  def setVersuche(zahl:Integer):Unit

  def getVersuche():Int

  def areYouWinningSon(guess:String):Boolean

  def createwinningboard():Unit
  
  
  //----------------------------------------------------------
  
              //board
  
  //----------------------------------------------------------

  def createGameboard():Unit

  def toString():String
  
  //----------------------------------------------------------
  
              //mode
  
  //----------------------------------------------------------

  def changeState(e: Int): Unit

  def getTargetword(): Map[Int, String]
  def TargetwordToString():String
  //==========================================================
  
              //!!!undoManger!!!
  
  //==========================================================

  def set(key:Int, feedback:Map[Int, String]):Unit

  def undo():Unit
  
  //==========================================================
  
              //!!!File!!!
  
  //==========================================================
  
  def save(): Unit

  def load():String
  
}
