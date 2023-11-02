package aview
import controller.controll
import src.util.wordle

import scala.io.StdIn.readLine

class TUI (controller: controll):
  var continue = true;
  val versuch = controller.limit//int
  val targetword= controller.targetword//string

  def run():Unit ={
    val n = 1
    println("Errate Wort:")
    println("_" * targetword.length)
    inputLoop(n)
    if (continue) println(s"Verloren! Versuche aufgebraucht. Lösung: $targetword")

  }
  def inputLoop(n:Int):Unit ={// do while schleife
    println(s"Versuch $n:")
    scanInput(readLine)
    if continue && controller.count(n) then inputLoop(n+1)
  }

  def scanInput(input: String): Unit ={
      input match
        case "quit" => {

          println(s"Wiedersehen")
          continue = false
          
        }
        case default =>{
          val guess = input
          if(guess == targetword){
            continue = false
            println("Du hast gewonnen!")
          } else controller.evaluateGuess(targetword, guess)//Fehler ist drin geht wenn man beendet nach Sieg in das jewals ältere spiel
        }
  }





