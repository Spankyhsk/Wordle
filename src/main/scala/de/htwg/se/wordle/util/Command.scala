package de.htwg.se.wordle.util

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemodeComponnent.gamemode

import scala.util.{Failure, Success, Try}

trait Command {
  def doStep: Unit
  def undoStep: Unit
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
