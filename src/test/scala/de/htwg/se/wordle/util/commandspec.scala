package de.htwg.se.wordle.util

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.{Game, GameInterface}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CommandSpec extends AnyWordSpec with Matchers {

  // Mock-Implementierung für den Command-Trait
  class TestCommand(controller: Option[controll]) extends Command {
    var doStepCalled = false
    var undoStepCalled = false

    override def doStep: Unit = { doStepCalled = true }
    override def undoStep: Unit = { undoStepCalled = true }
  }

  // Tests für den UndoManager
  "UndoManager" should {
    "correctly manage doStep and undoStep of a Command" in {
      val mockGame = new Game(null, null, null)
      val mockController = controll(mockGame, null)
      val command = new TestCommand(Some(mockController))
      val undoManager = new UndoManager()

      // Test doStep
      undoManager.doStep(command)
      command.doStepCalled should be(true)

      // Test undoStep
      undoManager.undoStep
      command.undoStepCalled should be(true)
    }
  }
}
