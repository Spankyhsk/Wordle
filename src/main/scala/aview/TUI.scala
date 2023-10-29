package aview
import controller.controll
import model.attempt
import src.util.wordle

import scala.io.StdIn.readLine

class TUI (controller: controll):
  var continue = true;
  val versuch = controller.versuch//int
  val targetword= controller.targetword//string

  def run():Unit ={
    val n = 0

    println("Errate Wort:")
    println("_" * targetword.length)
    inputLoop(n)
    if (continue) lose() else println("Auf Wiedersehen")

  }
  def inputLoop(n:Int):Unit ={// do while schleife
    println(s"Versuch $n:")
    scanInput(readLine)
    if continue && controller.count(n) then inputLoop(n+1)
  }

  def scanInput(input: String): Unit ={
      input match
        case "quit" => continue = false;
        case default =>{
          val guess = input
          if(guess == targetword) win() else controller.evaluateGuess(targetword, guess)//Fehler ist drin geht wenn man beendet nach Sieg in das jewals ältere spiel
        }
  }

  def win():Unit={
    println(s"Du hast gewonnen! Lösung: $targetword")
    wordle.game()
    System.exit(0)
  }

  def lose():Unit={
    println(s"Verloren! Versuche aufbegraucht. Lösung: $targetword")
    wordle.game()
  }

