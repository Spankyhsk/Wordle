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

    override def changeState(e: Int): Unit = {
      stateChanged = e
    }

    override def createGameboard(): Unit = {
      gameboardCreated = true
    }

    override def createwinningboard(): Unit = {
      winningBoardCreated = true
    }
  }

  "An EasyModeCommand" should {
    "change the game state to 1 and create game and winning boards" in {
      val mockGame = new Game(null, null, null)
      val mockController = new MockControll(mockGame)
      val command = EasyModeCommand(Some(mockController))

      command.execute()

      mockController.stateChanged should be(1)
      mockController.gameboardCreated should be(true)
      mockController.winningBoardCreated should be(true)
    }
  }

  "A MediumModeCommand" should {
    "change the game state to 2 and create game and winning boards" in {
      val mockGame = new Game(null, null, null)
      val mockController = new MockControll(mockGame)
      val command = MediumModeCommand(Some(mockController))

      command.execute()

      mockController.stateChanged should be(2)
      mockController.gameboardCreated should be(true)
      mockController.winningBoardCreated should be(true)
    }
  }

  "A HardModeCommand" should {
    "change the game state to 3 and create game and winning boards" in {
      val mockGame = new Game(null, null, null)
      val mockController = new MockControll(mockGame)
      val command = HardModeCommand(Some(mockController))

      command.execute()

      mockController.stateChanged should be(3)
      mockController.gameboardCreated should be(true)
      mockController.winningBoardCreated should be(true)
    }
  }

  // Fügen Sie hier zusätzliche Tests für doStep, undoStep und redoStep hinzu, wenn notwendig.
}
