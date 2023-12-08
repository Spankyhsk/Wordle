package de.htwg.se.wordle

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.aview.{GUISWING, TUI}
import de.htwg.se.wordle.model.gamemodeComponnent.gamemode
import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.model.*
import de.htwg.se.wordle.model.gamefieldComponent.gameboard
import de.htwg.se.wordle.model.gamemechComponent.GameMech

object wordle {
  def main(args:Array[String]): Unit = {
    
    val controller = controll(new Game(new GameMech(), new gameboard() , gamemode(1)))
    val tui = TUI(controller)
    val gui = new GUISWING(controller)
    tui.run()
    

  }
  
}

