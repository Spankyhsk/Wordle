
package de.htwg.se.wordle.controller
import de.htwg.se.wordle.util.Observable
import de.htwg.se.wordle.model.GameInterface
import de.htwg.se.wordle.model.FileIOComponent.*
import de.htwg.se.wordle.model.Game
import de.htwg.se.wordle.util.Event
import de.htwg.se.wordle.util.UndoManager

case class controll (game:GameInterface, file:FileIOInterface)extends ControllerInterface with Observable {


  //============================================================================

            //!!!GAME!!!

  //=============================================================================

  //-----------------------------------------------------------------------------

          //mech

  //-----------------------------------------------------------------------------

  val gamemech = game.getGamemech()

  def count(): Boolean = {
    val continue = game.count()
    if (!continue) {
      notifyObservers(Event.LOSE)
    }
    continue
  }

  def controllLength(n: Int): Boolean = {
    game.controllLength(n)
  }

  def controllRealWord(guess: String): Boolean = {
    game.controllRealWord(guess)
  }

  def evaluateGuess(guess: String): Map[Int, String] = {
    game.evaluateGuess(guess)
  }

  def GuessTransform(guess: String): String = {
    game.GuessTransform(guess)
  }

  def setVersuche(zahl: Integer): Unit = {
    game.setN(zahl)
  }

  def getVersuche(): Int = {
    game.getN()
  }

  def areYouWinningSon(guess: String): Boolean = {
    val won = game.areYouWinningSon(guess)
    if (won) {
      notifyObservers(Event.WIN)
    }
    won
  }

  def createwinningboard(): Unit = {
    game.createwinningboard()
    notifyObservers(Event.Move)
  }

  //----------------------------------------------------------------------------

          //board

  //----------------------------------------------------------------------------

  val gameboard = game.getGamefield()

  def createGameboard(): Unit = {
    game.createGameboard()
  }

  override def toString: String = {
    game.toString
  }

  //----------------------------------------------------------------------------

          //Mode

  //----------------------------------------------------------------------------

  var gamemode = game.getGamemode()

  def changeState(e: Int): Unit = {
    game.changeState(e)
    notifyObservers(Event.NEW)
  }

  def getTargetword(): Map[Int, String] = {
    game.getTargetword()
  }

  def TargetwordToString():String ={
    game.TargetwordToString()
  }

  //=============================================================================

          //!!!undoManger!!!

  //=============================================================================

  private val undoManager = new UndoManager

  def set(key: Int, feedback: Map[Int, String]): Unit = {
    undoManager.doStep(new SetCommand(key, feedback, this))
    notifyObservers(Event.Move)
  }

  def undo(): Unit = {
    undoManager.undoStep
    notifyObservers(Event.UNDO)
  }

  //=============================================================================

        //!!!File!!!

  //=============================================================================

  def save():Unit={
    file.save(game)
    notifyObservers(Event.Move)
  }

  def load():String={
    val message = file.load(game)
    notifyObservers(Event.Move)
    message
  }

}

object controll:
  def apply(kind:String):controll ={
    kind match {
      case "XML" => controll(Game("norm"), new FileIOXML)
      case "JSON" => controll(Game("norm"), new FileIOJSON)
    }
  }
