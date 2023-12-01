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

case class EasyModeCommand(controller: Option[controll]) extends Command {
  override def execute(): Unit = {
    controller.foreach { c =>
      c.changeState(1)
      c.createGameboard()
      c.createwinningboard()
      // c.setDifficulty("leicht")
    }  
  }

  override def doStep: Unit={}

  override def undoStep: Unit={}

  override def redoStep: Unit={}

}

case class MediumModeCommand(controller: Option[controll]) extends Command {
  override def execute(): Unit = {
    controller.foreach { c =>
      c.changeState(2)
      c.createGameboard()
      c.createwinningboard()
      // c.setDifficulty("leicht")
    }
  }

  override def doStep: Unit={}

  override def undoStep: Unit={}

  override def redoStep: Unit={}

}

case class HardModeCommand(controller: Option[controll]) extends Command {
  override def execute(): Unit = {
    controller.foreach { c =>
      c.changeState(3)
      c.createGameboard()
      c.createwinningboard()
      // c.setDifficulty("leicht")
    }
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


  
  def clearUndoStack(): Unit = {
    undoStack = Nil
  }

  def clearRedoStack(): Unit = {
    redoStack = Nil
  }

  
  
  
}
