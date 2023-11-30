package de.htwg.se.wordle.util


import de.htwg.se.wordle.controller
import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemode

import scala.util.{Failure, Success, Try}

trait Command {
  def execute(): Unit



}

case class EasyModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(1)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("leicht")
  }

}

case class MediumModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(2)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("mittel")
  }

}

case class HardModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(3)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("schwer")
  }

}

trait CommandUndo {
  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit
}


class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: CommandUndo) = {
    undoStack = command :: undoStack
    command.doStep
  }

  def undoStep = {
    undoStack match {
      case Nil =>
      case head :: stack => {
        head.undoStep
        undoStack = stack
        redoStack = head :: redoStack
      }
    }
  }
}
