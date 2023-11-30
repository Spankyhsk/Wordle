package de.htwg.se.wordle.util

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemode

import scala.util.{Failure, Success, Try}

trait Command {
  def execute(): Unit

  def doStep: Unit

  def undoStep: Unit

  def redoStep: Unit


}

case class EasyModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(1)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("leicht")
  }

  override def doStep: Unit={}

  override def undoStep: Unit={}

  override def redoStep: Unit={}

}

case class MediumModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(2)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("mittel")
  }

  override def doStep: Unit={}

  override def undoStep: Unit={}

  override def redoStep: Unit={}

}

case class HardModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(3)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("schwer")
  }

  override def doStep: Unit={}

  override def undoStep: Unit={}

  override def redoStep: Unit={}

}


class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command) = {
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
