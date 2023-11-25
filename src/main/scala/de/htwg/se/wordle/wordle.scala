package de.htwg.se.wordle

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.aview.{TUI,Gui}



object wordle {
  def main(args:Array[String]): Unit = {
    println("Willkommen zu Wordle")
    
    val controller = controll(gamemode.state)
    val tui = TUI(controller)
    tui.run()
    //val gui =new Gui(controller)
    //gui.start()
  }
  
}

