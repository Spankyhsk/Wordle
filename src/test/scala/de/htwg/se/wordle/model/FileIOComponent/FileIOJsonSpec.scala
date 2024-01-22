package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.{Game, GameInterface}
import de.htwg.se.wordle.model.gamefieldComponent.gameboard
import de.htwg.se.wordle.model.gamemechComponent.GameMech
import de.htwg.se.wordle.model.gamemodeComponnent.gamemode
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.{JsObject, JsValue, Json}

import java.io.{File, PrintWriter}

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

    "loading a game with different game states" should {
      "correctly change the game state based on the winningBoard size" in {
        val fileIO = new FileIOJSON
        val fileName = "game.json"

        // Funktion zum Schreiben einer Test-JSON-Datei
        def writeTestJson(jsonContent: JsObject): Unit = {
          val pw = new PrintWriter(new File(fileName))
          try {
            pw.write(Json.prettyPrint(jsonContent))
          } finally {
            pw.close()
          }
        }

        // Testdaten vorbereiten
        val jsonWithWinningBoardSize1 = Json.obj("game" -> Json.obj("mech" -> Json.obj("winningboard" -> Json.toJson(Map(1 -> false)))))
        val jsonWithWinningBoardSize2 = Json.obj("game" -> Json.obj("mech" -> Json.obj("winningboard" -> Json.toJson(Map(1 -> false, 2 -> false)))))
        val jsonWithWinningBoardSize4 = Json.obj("game" -> Json.obj("mech" -> Json.obj("winningboard" -> Json.toJson(Map(1 -> false, 2 -> false, 3 -> false, 4 -> false)))))

        // Testfälle durchführen
        writeTestJson(jsonWithWinningBoardSize1)
        val game1: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        fileIO.load(game1)
        game1.getGamemode().toString should include("Wort1:")

        writeTestJson(jsonWithWinningBoardSize2)
        val game2: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        fileIO.load(game2)
        game2.getGamemode().toString should include("Wort1:")

        writeTestJson(jsonWithWinningBoardSize4)
        val game3: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        fileIO.load(game3)
        game3.getGamemode().toString should include("Wort1:")
      }
    }

    "saving a game" should {
      "correctly serialize the gameboard into JSON" in {
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val fileIO = new FileIOJSON

        // Erstellen eines Gameboards mit spezifischen Daten
        game.getGamefield().setMap(Map(1 -> Map(1 -> "A", 2 -> "B"), 2 -> Map(1 -> "C", 2 -> "D")))

        val json = fileIO.gameToJson(game)

        // Überprüfen, ob das JSON korrekt serialisiert wurde
        (json \ "game" \ "board" \ "gameboard").as[Seq[JsObject]].size should be(2)
        (json \\ "gamefield").map(_.as[Map[String, String]]) should contain allOf(Map("1" -> "A", "2" -> "B"), Map("1" -> "C", "2" -> "D"))
      }
    }

    "loading a game" should {
      /*"correctly deserialize the gameboard from JSON" in {
        val fileIO = new FileIOJSON
        val fileName = "game.json"

        val jsonWithGameBoard = Json.obj(
          "game" -> Json.obj(
            "board" -> Json.obj(
              "gameboard" -> Json.toJson(
                Seq(
                  Json.obj("key" -> 1, "gamefield" -> Json.obj("1" -> "A", "2" -> "B")),
                  Json.obj("key" -> 2, "gamefield" -> Json.obj("1" -> "C", "2" -> "D"))
                )
              )
            )
          )
        )

        // Schreiben des JSON-Inhalts in eine Datei
        val pw = new PrintWriter(new File(fileName))
        try {
          pw.write(Json.prettyPrint(jsonWithGameBoard))
        } finally {
          pw.close()
        }

        // Laden des Spiels
        val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        fileIO.load(game)

        // Überprüfen, ob das Gameboard korrekt deserialisiert wurde
        game.getGamefield().getMap()(1).getMap() should contain allOf(1 -> "A", 2 -> "B")
        game.getGamefield().getMap()(2).getMap() should contain allOf(1 -> "C", 2 -> "D")
      }
      */


      "set the correct game mode based on the targetWord size" in {
        val fileIO = new FileIOJSON
        val fileName = "game.json"

        // Funktion zum Schreiben einer Test-JSON-Datei
        def writeTestJson(jsonContent: JsObject): Unit = {
          val pw = new PrintWriter(new File(fileName))
          try {
            pw.write(Json.prettyPrint(jsonContent))
          } finally {
            pw.close()
          }
        }

        // Beispiel für JSON-Inhalt mit einem Zielwort
        val jsonWithTargetWord = Json.obj(
          "game" -> Json.obj(
            "mode" -> Json.obj(
              "TargetWord" -> Json.toJson(Map(1 -> "WORT", 2 -> "WORT2"))
            )
          )
        )

        // Schreiben des JSON-Inhalts in eine Datei und Laden des Spiels
        writeTestJson(jsonWithTargetWord)
        val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        fileIO.load(game)

        // Überprüfen, ob der richtige Spielmodus basierend auf der Größe des Zielworts gesetzt wurde
        game.getGamemode().toString should include("Wort1:")
      }
    }

    "deserializing a gameboard from JSON" should {
      "correctly convert JSON to the gameboard map structure" in {
        val fileIO = new FileIOJSON

        // Erstellen eines JSON-Objekts, das das Gameboard repräsentiert
        val jsonGameBoard = Json.arr(
          Json.obj("key" -> 1, "gamefield" -> Json.toJson(Map("1" -> "A", "2" -> "B"))),
          Json.obj("key" -> 2, "gamefield" -> Json.toJson(Map("1" -> "C", "2" -> "D")))
        )

        // Konvertieren des JsArray in eine Seq[JsValue]
        val gameBoardSeq: Seq[JsValue] = jsonGameBoard.value.toSeq

        // Aufruf der gameboardFromJason Methode
        val result = fileIO.gameboardFromJason(gameBoardSeq)

        // Überprüfen, ob die resultierende Map die erwarteten Werte enthält
        result(1) should contain allOf(1 -> "A", 2 -> "B")
        result(2) should contain allOf(1 -> "C", 2 -> "D")
      }
    }


  }
}
  
