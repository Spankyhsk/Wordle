package de.htwg.se.wordle.util

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.{Game, GameInterface}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CommandSpec extends AnyWordSpec with Matchers {

  // Einfache Mock-Implementierung für controll
  class MockControll(game: GameInterface) extends controll(game, null) {
    var stateChanged: Int = 0
    var gameboardCreated: Boolean = false
    var winningBoardCreated: Boolean = false
    var doStepsCalled: Int = 0
    var undoStepsCalled: Int = 0

    override def changeState(e: Int): Unit = {
      stateChanged = e
    }

    override def createGameboard(): Unit = {
      gameboardCreated = true
    }

    override def createwinningboard(): Unit = {
      winningBoardCreated = true
    }

    def simulateDoStep(): Unit = {
      doStepsCalled += 1
    }

    def simulateUndoStep(): Unit = {
      undoStepsCalled += 1
    }
  }




  "An EasyModeCommand" should {
    val mockGame = new Game(null, null, null)
    val mockController = new MockControll(mockGame)
    val command = new EasyModeCommand(Some(mockController)) {
      override def doStep: Unit = mockController.simulateDoStep()

      override def undoStep: Unit = mockController.simulateUndoStep()
    }
    val undoManager = new UndoManager()

    "change the game state to 1 and create game and winning boards" in {
      command.execute()

      mockController.stateChanged should be(1)
      mockController.gameboardCreated should be(true)
      mockController.winningBoardCreated should be(true)
    }

    "properly manage doStep and undoStep" in {
      undoManager.doStep(command)
      mockController.doStepsCalled should be(1)

      //undoManager.undoStep()
      //mockController.undoStepsCalled should be(1)
    }
  }


  "A MediumModeCommand" should {
    val mockGame = new Game(null, null, null)
    val mockController = new MockControll(mockGame)
    val command = new EasyModeCommand(Some(mockController)) {
      override def doStep: Unit = mockController.simulateDoStep()

      override def undoStep: Unit = mockController.simulateUndoStep()
    }
    val undoManager = new UndoManager()

    "change the game state to 2 and create game and winning boards" in {
      val mockGame = new Game(null, null, null)
      val mockController = new MockControll(mockGame)
      val command = MediumModeCommand(Some(mockController))

      command.execute()

      mockController.stateChanged should be(2)
      mockController.gameboardCreated should be(true)
      mockController.winningBoardCreated should be(true)
    }

    "properly manage doStep and undoStep" in {
      undoManager.doStep(command)
      mockController.doStepsCalled should be(1)

      //undoManager.undoStep()
      //mockController.undoStepsCalled should be(1)
    }
  }

  "A HardModeCommand" should {
    val mockGame = new Game(null, null, null)
    val mockController = new MockControll(mockGame)
    val command = new EasyModeCommand(Some(mockController)) {
      override def doStep: Unit = mockController.simulateDoStep()

      override def undoStep: Unit = mockController.simulateUndoStep()
    }
    val undoManager = new UndoManager()
    "change the game state to 3 and create game and winning boards" in {
      val mockGame = new Game(null, null, null)
      val mockController = new MockControll(mockGame)
      val command = HardModeCommand(Some(mockController))

      command.execute()

      mockController.stateChanged should be(3)
      mockController.gameboardCreated should be(true)
      mockController.winningBoardCreated should be(true)
    }
    "properly manage doStep and undoStep" in {
      undoManager.doStep(command)
      mockController.doStepsCalled should be(1)

      //undoManager.undoStep()
      //mockController.undoStepsCalled should be(1)
    }
  }


}
