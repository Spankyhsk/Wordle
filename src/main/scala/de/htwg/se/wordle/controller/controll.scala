
package de.htwg.se.wordle.controller
import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.model.gamefield.{gamefield, gameboard}
import de.htwg.se.wordle.model.gamemech
import de.htwg.se.wordle.util.Observable

case class controll (gm: gamemode.State)extends Observable {

  var gamemode = gm
  val limit = gamemode.getLimit()//muss geupdatet werden wenn state gewechselt wird
  var targetword = gamemode.getTargetword()// muss geupdatet werden wenn state gewechselt wird
  val gamemech = new gamemech()
  val gameboard = new gameboard()

  def count(n:Int):Boolean={
    val bool = gamemech.count(n, limit)
    bool
  }
  def createGameboard():Unit ={
    gameboard.buildGameboard(targetword.size, 1, new gamefield())
    createGamefieldR(1)
    notifyObservers
  }
  def createGamefieldR(n:Int):Unit ={
    gameboard.getChilderen(n).buildGamefield(limit, 1, "_"*targetword(1).length)
    if(n<gameboard.map.size) createGamefieldR(n+1)
  }

  def set(key:Int,feedback:String):Unit={
    setR(1, key, feedback)
    notifyObservers
  }
  def setR(n:Int, key:Int, feedback:String):Unit={
    gameboard.getChilderen(n).set(key, feedback)
    if(n<gameboard.map.size) setR(n+1, key, feedback)
  }


  def evaluateGuess(targetWord: String, guess: String): String = {
    val feedback = gamemech.evaluateGuess(targetWord, guess)
    feedback
  }

  override def toString: String = {gameboard.toString}

  def changeState(e:Int):Unit={
    gamemode = gm.handle(e)
    targetword = gamemode.getTargetword()
  }


}
