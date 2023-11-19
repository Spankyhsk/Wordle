
package de.htwg.se.wordle.controller
import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.model.gamefield.{gamefield, gameboard}
import de.htwg.se.wordle.model.gamemech
import de.htwg.se.wordle.util.Observable

case class controll (gm: gamemode.State)extends Observable {

  var gamemode = gm
  val gamemech = new gamemech()
  val gameboard = new gameboard()

  def count(n:Int):Boolean={
    val bool = gamemech.count(n, getLimit())
    bool
  }
  def createGameboard():Unit ={
    gameboard.buildGameboard(getTargetword().size, 1, new gamefield())
    createGamefieldR(1)
    notifyObservers
  }
  def createGamefieldR(n:Int):Unit ={
    gameboard.getChilderen(n).buildGamefield(getLimit(), 1, "_"*getTargetword()(1).length)
    if(n<gameboard.map.size) createGamefieldR(n+1)
  }

  def set(key:Int,feedback:Map[Int, String]):Unit={
    setR(1, key, feedback)
    notifyObservers
  }
  def setR(n:Int, key:Int, feedback:Map[Int, String]):Unit={
    gameboard.getChilderen(n).set(key, feedback(n))
    if(n<gameboard.map.size) setR(n+1, key, feedback)
  }


  def evaluateGuess(guess: String): Map[Int, String] = {
    val keys: List[Int] = getTargetword().keys.toList
    val feedback: Map[Int, String] = keys.map(key => key -> gamemech.evaluateGuess(getTargetword()(key), guess)).toMap
    println(feedback)
    feedback
  }



  override def toString: String = {gameboard.toString}

  def changeState(e:Int):Unit={
    gamemode = gm.handle(e)

  }

  def getTargetword():Map[Int, String]={
    val targetword = gamemode.getTargetword()
    targetword
  }

  def getLimit():Int={
    val limit = gamemode.getLimit()
    limit
  }

  /*def compareTargetguess(guess:String):Boolean={
    val mapvalue = getTargetword().values
    mapvalue.foreach(value => gamemech.compareTargetguess(value, guess))
  }*/


}
