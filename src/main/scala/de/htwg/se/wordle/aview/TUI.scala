package de.htwg.se.wordle.aview

import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{Event, Observer}


class TUI (controller: ControllerInterface)extends Observer:
  controller.add(this)
  var newgame = true

  def getnewgame(): Boolean = {
    newgame
  }

  def processInput(input: String): Unit = {
    if(newgame){
      controller.changeState(difficultyLevel(input))
      controller.createGameboard()
      controller.createwinningboard()
    }else{
      scanInput(input)
    }
  }

  def difficultyLevel(input: String): Int = {
    try {
      val level = input.toInt
      if (level > 0 && level < 4) {
        level
      } else {
        println("Falsche Angabe, es wird Level Einfach angefangen")
        1
      }
    } catch {
      case _: NumberFormatException =>
        println("Falsche Angabe, es wird Level Einfach angefangen")
        1
    }
  }
  

  def scanInput(input: String): Unit ={
      input match
        case "$quit" => {
          println(s"Wiedersehen")
          sys.exit(0)
        }
        case "$undo"=>{
          controller.undo()
        }
        case "$save"=>{
          println("Spielstand wurde gespeichert")
          controller.save()
        }
        case "$load"=>{
          println("Spielstand wird geladen")
          val message = controller.load()
          println(message)
        }
        case "$switch"=>{
          newgame = true
        }
        case default =>{
          val guess = controller.GuessTransform(input)
          if(controller.controllLength(guess.length) && controller.controllRealWord(guess)) {
            if (!controller.areYouWinningSon(guess)&&controller.count()) {
              controller.set(controller.getVersuche(), controller.evaluateGuess(guess))
              controller.setVersuche(controller.getVersuche() + 1)
            }else{
              controller.set(controller.getVersuche(), controller.evaluateGuess(guess))
            }
          }else{
            println("Falsche Eingabe")
            println("Dein Tipp:")
          }

        }
  }
  override def update(e:Event):Unit = {
    e match
      case Event.Move=> {
        println(controller.toString)
        if(!newgame) {
          println("Dein Tipp: ")
        }
      }
      case Event.NEW=>{
        controller.setVersuche(1)
        newgame = false
        println("Errate Wort:") //guess
      }
      case Event.UNDO=>{
        controller.setVersuche(controller.getVersuche()-1)
        println(controller.toString)
        println("Dein Tipp: ")
      }
      case Event.WIN =>{
        println(s"Du hast gewonnen! Lösung: "+ controller.TargetwordToString())
        newgame = true

      }
      case Event.LOSE =>{
        println(s"Verloren! Versuche aufgebraucht. Lösung: "+ controller.TargetwordToString())
        newgame = true

      }
  }





