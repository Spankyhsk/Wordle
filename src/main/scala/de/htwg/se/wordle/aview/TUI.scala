package de.htwg.se.wordle.aview

import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{Event, Observer}

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

class TUI (controller: ControllerInterface)extends Observer:
  controller.add(this)
  var continue = true
  var stepback = false

  def run():Unit ={
    println("Willkommen zu Wordle")
    println("Befehle")
    println("$quit := Spielbeenden, $save := Speichern, $load := Laden, $switch := Schwirigkeit verändern")
    println("Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer")
    controller.changeState(readLine.toInt)
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
          controller.save()
        }
        case "$load"=>{
          controller.load()
        }
        case "$switch"=>{
          println("Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer")
          controller.changeState(readLine.toInt)
        }
        case default =>{
          val guess = input.toUpperCase//ändert alle klein buchstaben in großbuchstaben
          controller.areYouWinningSon(guess)
          controller.count(controller.getVersuche())
          controller.setVersuche(controller.getVersuche() +1)
          controller.set(controller.getVersuche(), controller.evaluateGuess(guess))

        }
  }
  override def update(e:Event):Unit = {
    e match
      case Event.Move=> {
        println(controller.toString)
        scanInput(readLine)
      }
      case Event.NEW=>{
        controller.setVersuche(0)
        println("Errate Wort:") //guess
        controller.createGameboard()
        controller.createwinningboard()
      }
      case Event.UNDO=>{
        println(controller.toString)
        scanInput(readLine)
      }
      case Event.WIN =>{
        println(s"Du hast gewonnen! Lösung:"+ controller.getTargetword())
        println("Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer")
        controller.changeState(readLine.toInt)
      }
      case Event.LOSE =>{
        println(s"Verloren! Versuche aufgebraucht. Lösung:"+ controller.getTargetword())
        println("Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer")
        controller.changeState(readLine.toInt)

      }
  }





