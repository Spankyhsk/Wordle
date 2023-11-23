package de.htwg.se.wordle.util

import scala.util.{Try, Success, Failure}

trait Command[T]:
  def noStep(t: T): Try[T]
  def doStep(t: T): Try[T]
  def undoStep(t: T): Try[T]
  def redoStep(t: T): Try[T]

class UndoManager[T]:
  private var undoStack: List[Command[T]] = Nil
  private var redoStack: List[Command[T]] = Nil
  def doStep(t: T, command: Command[T]): Try[T] =
    undoStack = command :: undoStack
    command.doStep(t)
  def undoStep(t: T): Try[T] =
    undoStack match {
      case Nil => Success(t)
      case head :: stack => {
        val result = head.undoStep(t)
        undoStack = stack
        redoStack = head :: redoStack
        result
      }
    }
  def redoStep(t: T): Try[T] =
    redoStack match {
      case Nil => Success(t)
      case head :: stack => {
        val result = head.redoStep(t)
        redoStack = stack
        undoStack = head :: undoStack
        result
      }
    }