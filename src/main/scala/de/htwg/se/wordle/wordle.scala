package de.htwg.se.wordle

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.aview.{TUI, GUISWING}
import de.htwg.se.wordle.model.gamemodeComponnent.gamemode


object wordle {
  def main(args:Array[String]): Unit = {
    
    val controller = controll(gamemode.state)
    val tui = TUI(controller)
    val gui = new GUISWING(controller)
    tui.run()
    

  }
  
}

