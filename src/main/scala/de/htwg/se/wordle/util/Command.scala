package de.htwg.se.wordle.util


import de.htwg.se.wordle.controller
import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemode

import scala.util.{Failure, Success, Try}

trait Command {
  def execute(): Unit
  def undo(): Unit
}

// Concrete command class for changing state
class ChangeStateCommand(controller: controll, newState: gamemode.State) extends Command {
  private val previousState = controller.gamemode // Store the previous state for undo

  override def execute(): Unit = {
    controller.gamemode = newState
    controller.createGameboard()
    controller.createwinningboard()
  }

  override def undo(): Unit = {
    controller.gamemode = previousState
    controller.createGameboard()
    controller.createwinningboard()
  }
}