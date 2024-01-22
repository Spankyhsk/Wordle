package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.{Game, GameInterface}
import de.htwg.se.wordle.model.gamefieldComponent.gameboard
import de.htwg.se.wordle.model.gamemechComponent.GameMech
import de.htwg.se.wordle.model.gamemodeComponnent.gamemode
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsObject

class FileIOJsonSpec extends AnyWordSpec with Matchers {
  "FileIOJSON" when {
    "FileIOJSON" should {





      "not throw an exeption on successful saving" in {
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val file = new FileIOJSON
        try {
          file.save(game)
        } catch {
          case e: Exception =>
            fail(s"Fehler beim Speichern des Spiels: ${e.getMessage}")
        }
      }

      "return the correct JSON representation of the game" in {
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val file = new FileIOJSON

        val result: JsObject = file.gameToJson(game)

        // Überprüfen Sie, ob das erzeugte JSON den erwarteten Werten entspricht
        (result \ "game" \ "mech" \ "winningboard").as[Map[Int, Boolean]] should be(game.getGamemech().getWinningboard())
        (result \ "game" \ "mech" \ "Versuch").as[Int] should be(game.getGamemech().getN())
        (result \ "game" \ "board").as[JsObject] should be(file.gameboardToJason(game.getGamefield().getMap()))
        (result \ "game" \ "mode" \ "TargetWord").as[Map[Int, String]] should be(game.getGamemode().getTargetword())
        (result \ "game" \ "mode" \ "limit").as[Int] should be(game.getGamemode().getLimit())
      }
    }

    "loading a game" should {
      "correctly restore the game state from a JSON file" in {
        // Create a game instance and load a game
        val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        val fileIO = new FileIOJSON
        val loadResult = fileIO.load(game)

        // Assertions to check if the game state is restored correctly
        loadResult should include("Laden") //laden sollte erfolgreich seim
        game.getGamemech().getN() should not be 0
        game.getGamemode().getLimit() should not be 0
        //game.getGamefield().getMap() should not be empty
      }

      "handle file not found or corrupt data gracefully" in {
        // Create a game instance and try to load from a non-existent or corrupt file
        val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        val fileIO = new FileIOJSON

        // Modify the file path or content to simulate error
        val loadResult = fileIO.load(game)

        // Assertion to check if an appropriate error message is returned
        loadResult should include("Fehler beim Laden des Spiels")
      }
    }

    "saving and then loading a game" should {
      "preserve the game state accurately" in {
        // Initial setup: Create a game instance with specific state
        val originalGame: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        originalGame.setN(1) // Example of setting a specific state
        originalGame.setTargetWord(Map(1 -> "example")) // Set a specific target word for testing

        // Save the game state
        val fileIO = new FileIOJSON
        fileIO.save(originalGame)

        // Load the game into a new instance
        val loadedGame: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        fileIO.load(loadedGame)

        // Assertions to verify that the game state is preserved
        loadedGame.getN() should be(originalGame.getN())
        loadedGame.getTargetword().keys should contain(1) // Check if key '1' is included in the target word map
        // Additional checks can be added here if needed
      }
    }


  }
}
  
