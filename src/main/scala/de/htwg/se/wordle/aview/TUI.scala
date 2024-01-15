package de.htwg.se.wordle.aview

import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{Event, Observer}


import scala.util.{Failure, Success, Try}

class TUI (controller: ControllerInterface)extends Observer:
  controller.add(this)
  var newgame = true
  
  def getOutput():Unit={
    if(newgame){
      println("Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer")
    }
  }

  def processInput(input: String): Unit = {
    if(newgame){
      controller.changeState(input.toInt)
      controller.createGameboard()
      controller.createwinningboard()
    }else{
      scanInput(input)
    }
  }
  

  def scanInput(input: String): Unit ={
      input match
        case "$quit" => {
          println(s"Wiedersehen")
          sys.exit(0)
        }
        case "$redo"=>{
          controller.undo()
        }
        case "$save"=>{
          println("Spielstand wurde gespeichert")
          controller.save()
        }
        case "$load"=>{
          println("Spielstand wird geladen")
          controller.load()
        }
        case "$switch"=>{
          newgame = true
        }
        case default =>{
          val guess = input.toUpperCase//ändert alle klein buchstaben in großbuchstaben
          controller.areYouWinningSon(guess)
          controller.count(controller.getVersuche())
          controller.set(controller.getVersuche(), controller.evaluateGuess(guess))
          controller.setVersuche(controller.getVersuche() +1)

        }
  }
  override def update(e:Event):Unit = {
    e match
      case Event.Move=> {
        println(controller.toString)
        println("Dein Tipp: ")
      }
      case Event.NEW=>{
        controller.setVersuche(1)
        newgame = false
        println("Errate Wort:") //guess
      }
      case Event.UNDO=>{
        println(controller.toString)
      }
      case Event.WIN =>{
        println(s"Du hast gewonnen! Lösung:"+ controller.getTargetword())
        newgame = true

      }
      case Event.LOSE =>{
        println(s"Verloren! Versuche aufgebraucht. Lösung:"+ controller.getTargetword())
        newgame = true

      }
  }





