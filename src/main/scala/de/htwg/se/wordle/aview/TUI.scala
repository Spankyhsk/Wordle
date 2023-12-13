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
    val n = 1
    println("Willkommen zu Wordle")
    println("Gamemode aussuchen: \n1:= leicht\n2:=mittel\n3:=schwer")
    controller.changeState(readLine.toInt)
    println(controller.getTargetword())//Entfernen wenn alles funkzioniert
    println("Errate Wort:")//guess
    controller.createGameboard()
    controller.createwinningboard()
    inputLoop(n)
    if (continue) println(s"Verloren! Versuche aufgebraucht. Lösung:"+ controller.getTargetword())
  }
  def inputLoop(n:Int):Unit ={// do while schleife
    controller.toString
    scanInput(readLine,n)
    if(stepback){
      stepback = false
      inputLoop(n-1)
    }
    if continue && controller.count(n) then inputLoop(n+1)
  }

  def scanInput(input: String, n:Int): Unit ={
      input match
        case "quit" => {
          println(s"Wiedersehen")
          continue = false
        }
        case "$"=>{
          controller.undo()
          stepback = true
        }
        case default =>{
          val guess = input.toUpperCase//ändert alle klein buchstaben in großbuchstaben
          if(controller.areYouWinningSon(guess)){
            continue = false
            println(s"Du hast gewonnen! Lösung:"+ controller.getTargetword())
          } else controller.set(n, controller.evaluateGuess(guess))
        }
  }
  override def update(e:Event):Unit = {
    e match
      case Event.Move=> println(controller.toString)
      case Event.NEW=>{
        val n = 1
        println("Errate Wort:") //guess
        controller.createGameboard()
        controller.createwinningboard()
        inputLoop(n)
      }
  }





