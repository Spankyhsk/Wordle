package de.htwg.se.wordle


import de.htwg.se.wordle.aview.{GUISWING, TUI}
import com.google.inject.Guice
import de.htwg.se.wordle.controller.ControllerInterface

object wordle {
  def main(args:Array[String]): Unit = {
    val injector = Guice.createInjector(new WordleModule)
    val c = injector.getInstance(classOf[ControllerInterface])
    val tui = new TUI(c)
    val gui = new GUISWING(c)
    tui.run()
    

  }
  
}

