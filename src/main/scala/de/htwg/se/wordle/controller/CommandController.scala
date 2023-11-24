package de.htwg.se.wordle.controller
import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.aview.TUI
import de.htwg.se.wordle.util.Command
import de.htwg.se.wordle.util.Observer
import de.htwg.se.wordle.util.UndoManager
import scala.util.Success
import scala.util.Failure
import scala.util.Try



import de.htwg.se.wordle.model.gamemode
import de.htwg.se.wordle.util.{Command, UndoManager}

class GuessCommand(controller: controll, guess: String) extends Command[controll] {
  private var prevGameState: controll = controller.copy()


  override def doStep(t: controll): Try[controll] = {
    prevGameState = t.copy()
    Try(t.evaluateGuess(guess)).map(feedback => {
      val updatedController = t.copy()
      updatedController.set(1, feedback)
      updatedController
    })
  }


  override def undoStep(t: controll): Try[controll] = {
    val result = prevGameState.copy()
    prevGameState = t.copy()
    Success(result)
  }

  override def redoStep(t: controll): Try[controll] = {
    val result = t.copy()
    doStep(result)
  }

  override def noStep(t: controll): Try[controll] = Success(t)
}
