package de.htwg.se.wordle.aview

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.util.Observer

import scala.io.StdIn.readLine

class TUI (controller: controll)extends Observer:
  controller.add(this)
  var continue = true;
  val numberTrys = controller.limit//int//updatet sich nicht//getmethode schreiben das erste nach state wechsel difiniert
  val targetword= controller.targetword//string//updatet sich nicht//getmethode schreiben das erste nach state wechsel difiniert

  def run():Unit ={
    val n = 1
    println("Gamemode aussuchen: \n1:= leicht\n 2:=mittel\n 3:=schwer")
    controller.changeState(readLine.toInt)
    println(targetword)
    println("Errate Wort:")//guess
    controller.createGameboard()
    inputLoop(n)
    if (continue) println(s"Verloren! Versuche aufgebraucht. Lösung: $targetword")

  }
  def inputLoop(n:Int):Unit ={// do while schleife
    controller.toString
    scanInput(readLine, n)
    if continue && controller.count(n) then inputLoop(n+1)
  }

  def scanInput(input: String, n:Int): Unit ={
      input match
        case "quit" => {

          println(s"Wiedersehen")
          continue = false
          
        }
        
        case default =>{
          val guess = input.toUpperCase//ändert alle klein buchstaben in großbuchstaben
          if(guess == targetword(1)){
            continue = false
            println(s"Du hast gewonnen! Lösung: $targetword")
          } else controller.set(n, controller.evaluateGuess(targetword(1), guess))
        }
        
        
  }
  override def update:Unit = println(controller.toString)





