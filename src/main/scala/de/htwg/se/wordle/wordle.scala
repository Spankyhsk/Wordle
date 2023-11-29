package de.htwg.se.wordle

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.aview.{TUI, GUISWING}



object wordle {
  def main(args:Array[String]): Unit = {
    
    val controller = controll(gamemode.state)
    //val tui = TUI(controller)
    //tui.run()
    val gui = new GUISWING(controller)
    

  }
  
}

