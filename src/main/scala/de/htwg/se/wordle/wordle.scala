package de.htwg.se.wordle


import de.htwg.se.wordle.aview.{GUISWING, TUI}
import com.google.inject.Guice
import de.htwg.se.wordle.controller.ControllerInterface
import scala.io.StdIn.readLine

object wordle {
  def main(args:Array[String]): Unit = {
    val injector = Guice.createInjector(new WordleModuleJson)
    val controll = injector.getInstance(classOf[ControllerInterface])
    val tui = new TUI(controll)
    val gui = new GUISWING(controll)


    println("Willkommen zu Wordle")
    println("Befehle")
    println("$quit := Spiel beenden, $save := Speichern, $load := Laden, $switch := Schwierigkeit verändern")
    while(true){
      if (tui.getnewgame()) {
        println("Gamemode aussuchen: \n1:= leicht\n2:= mittel\n3:= schwer")
      }
      tui.processInput(readLine())
    }

  }
  
}

