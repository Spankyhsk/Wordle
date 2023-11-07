package de.htwg.se.wordle

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.{attempt, word}
import de.htwg.se.wordle.aview.TUI

import scala.util.Random

object wordle {
  def main(args:Array[String]): Unit = {
    println("Willkommen zu Wordle")
    //Lösungswort raussuchen
    val wordObjekt = new word()
    val attempt = new attempt(selectRandomWord(wordObjekt.wordsByLength(2)), 3) //Skalierbar (Wortlänge, Versuche)
    val controller = controll(attempt)
    val tui = TUI(controller)
    tui.run()
  }

  def selectRandomWord(wordArray: Array[String]): String = {
    Random.shuffle(wordArray.toList).head
  }
}
