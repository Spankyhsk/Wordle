
package de.htwg.se.wordle.controller
import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.model.gamefield.{gamefield, gameboard}
import de.htwg.se.wordle.model.gamemech.GameMech
import de.htwg.se.wordle.util.Observable

case class controll (gm: gamemode.State)extends Observable {

  var gamemode = gm
  val gamemech = new GameMech()
  val gameboard = new gameboard()

  def count(n:Int):Boolean={
    val bool = gamemech.count(n, getLimit())
    bool
  }
  def createGameboard():Unit ={
    gameboard.buildGameboard(getTargetword().size, 1)
    createGamefieldR(1)
    notifyObservers
  }
  def createGamefieldR(n:Int):Unit ={
    gameboard.getChilderen(n).buildGamefield(getLimit(), 1, s"_"*getTargetword()(1).length)
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

  def createwinningboard():Unit={
    gamemech.buildwinningboard(gameboard.map.size, 1)
  }
  def areYouWinningSon(guess:String):Boolean={
    gamemech.compareTargetGuess(1, getTargetword(),guess)
    gamemech.areYouWinningSon()
  }
}

/*
// Memento.scala
package de.htwg.se.wordle.controller

case class Memento(state: controll)

// controll.scala
package de.htwg.se.wordle.controller

import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.model.gamefield.{gamefield, gameboard}
import de.htwg.se.wordle.model.gamemech.GameMech
import de.htwg.se.wordle.util.Observable

case class controll (gm: gamemode.State)extends Observable {
  var gamemode = gm
  val gamemech = new GameMech()
  val gameboard = new gameboard()

  private var memento: Memento = createMemento()

  // ... (andere Methoden)

  def createMemento(): Memento = {
    Memento(this)
  }

  def setMemento(m: Memento): Unit = {
    memento = m
  }

  def getState(): controll = {
    memento.state
  }

  def count(n: Int): Boolean = {
    val bool = gamemech.count(n, getLimit())
    bool
  }

  def createGameboard(): Unit = {
    gameboard.buildGameboard(getTargetword().size, 1)
    createGamefieldR(1)
    notifyObservers
    saveState()
  }

  def createGamefieldR(n: Int): Unit = {
    gameboard.getChilderen(n).buildGamefield(getLimit(), 1, s"_" * getTargetword()(1).length)
    if (n < gameboard.map.size) createGamefieldR(n + 1)
  }

  def set(key: Int, feedback: Map[Int, String]): Unit = {
    setR(1, key, feedback)
    notifyObservers
    saveState()
  }

  def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit = {
    gameboard.getChilderen(n).set(key, feedback(n))
    if (n < gameboard.map.size) setR(n + 1, key, feedback)
  }

  def evaluateGuess(guess: String): Map[Int, String] = {
    val keys: List[Int] = getTargetword().keys.toList
    val feedback: Map[Int, String] = keys.map(key => key -> gamemech.evaluateGuess(getTargetword()(key), guess)).toMap
    feedback
  }

  override def toString: String = {
    gameboard.toString
  }

  def changeState(e: Int): Unit = {
    gamemode = gm.handle(e)
    saveState()
  }

  def getTargetword(): Map[Int, String] = {
    val targetword = gamemode.getTargetword()
    targetword
  }

  def getLimit(): Int = {
    val limit = gamemode.getLimit()
    limit
  }

  def createwinningboard(): Unit = {
    gamemech.buildwinningboard(gameboard.map.size, 1)
    saveState()
  }

  def areYouWinningSon(guess: String): Boolean = {
    gamemech.compareTargetGuess(1, getTargetword(), guess)
    gamemech.areYouWinningSon()
  }

  private def saveState(): Unit = {
    setMemento(createMemento())
  }
}

*/