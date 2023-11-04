package de.htwg.se.wordle.aview

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.util.Observer

import scala.io.StdIn.readLine

class TUI (controller: controll)extends Observer:

  var continue = true;
  val numberTrys = controller.limit//int
  val targetword= controller.targetword//string

  def run():Unit ={
    val n = 1
    println("Errate Wort:")
    println(s"Anzahl der Versuche: $numberTrys")
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
          val guess = input.toUpperCase//ändert alle klein buchstaben in großbuchstaben
          if(guess == targetword){
            continue = false
            println(s"Du hast gewonnen! Lösung: $targetword")
          } else println(controller.evaluateGuess(targetword, guess))
        }
        
        
  }
  override def update:Unit = println(controller.evaluateGuess(targetword, guess))





