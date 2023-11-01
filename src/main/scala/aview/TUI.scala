package aview
import controller.controll
import src.util.wordle

import scala.io.StdIn.readLine

class TUI (controller: controll):
  var continue = true;
  val versuch = controller.versuch//int
  val targetword= controller.targetword//string

  def run():Unit ={
    val n = 1
    println("Errate Wort:")
    println("_" * targetword.length)
    inputLoop(n)
    if (continue) println(s"Verloren! Versuche aufbegraucht. Lösung: $targetword") else println(s"Du hast gewonnen! Lösung: $targetword")
    
  }
  def inputLoop(n:Int):Unit ={// do while schleife
    println(s"Versuch $n:")
    scanInput(readLine)
    if continue && controller.count(n) then inputLoop(n+1)
  }

  def scanInput(input: String): Unit ={
      input match
        case "quit" => {
          println("Wiedersehen")
          System.exit(0)
        }
        case default =>{
          val guess = input
          if(guess == targetword) continue = false else controller.evaluateGuess(targetword, guess)//Fehler ist drin geht wenn man beendet nach Sieg in das jewals ältere spiel
        }
  }


