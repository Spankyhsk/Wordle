package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.{Game, GameInterface}
import de.htwg.se.wordle.model.gamefieldComponent.gameboard
import de.htwg.se.wordle.model.gamemechComponent.GameMech
import de.htwg.se.wordle.model.gamemodeComponnent.{gamemode, gamemode3}
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
      "loading a game" should {
        "correctly deserialize the gameboard from JSON" in {
          val fileIO = new FileIOJSON
          val fileName = "game.json"

          // JSON-Struktur basierend auf Ihrem Beispiel
          val jsonWithGameBoard = Json.parse(
            """
                {
                  "game": {
                    "mech": {
                      "winningboard": {"1": false, "2": false},
                      "Versuch": 2
                    },
                    "board": {
                      "gameboard": [
                        {
                          "key": 1,
                          "gamefield": {
                            "5": "?????", "1": "SPIEL", "6": "?????", "2": "?????",
                            "7": "?????", "3": "?????", "4": "?????"
                          }
                        },
                        {
                          "key": 2,
                          "gamefield": {
                            "5": "?????", "1": "SPIEL", "6": "?????", "2": "?????",
                            "7": "?????", "3": "?????", "4": "?????"
                          }
                        }
                      ]
                    },
                    "mode": {
                      "TargetWord": {"1": "BLICK", "2": "BLICK"},
                      "limit": 7
                    }
                  }
                }
              """)

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
          val gameboardMap = game.getGamefield().getMap()

          // Überprüfen, ob die Schlüssel wie erwartet vorhanden sind
          gameboardMap should contain key 1
          gameboardMap should contain key 2

          // Überprüfen des Inhalts des ersten Gamefields
          val gamefield1 = gameboardMap(1).getMap()
          gamefield1 should contain(1 -> "SPIEL")
          gamefield1 should contain(5 -> "?????")

          // Überprüfen des Inhalts des zweiten Gamefields
          val gamefield2 = gameboardMap(2).getMap()
          gamefield2 should contain(1 -> "SPIEL")
          gamefield2 should contain(5 -> "?????")
        }
      }


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

    "gameboardFromJason" should {
      "correctly convert JSON sequence to gameboard map structure" in {
        val fileIO = new FileIOJSON

        // Erstellen einer JSON-Seq, die ein Gameboard repräsentiert
        val jsonSeq = Seq(
          Json.obj("key" -> 1, "gamefield" -> Json.obj("1" -> "A", "2" -> "B")),
          Json.obj("key" -> 2, "gamefield" -> Json.obj("1" -> "C", "2" -> "D"))
        )

        // Konvertieren der JSON-Seq in eine Map
        val result = fileIO.gameboardFromJason(jsonSeq)

        // Überprüfen der korrekten Umwandlung
        result(1) should be(Map(1 -> "A", 2 -> "B"))
        result(2) should be(Map(1 -> "C", 2 -> "D"))
      }
    }

    "loading a game with incorrect JSON format" should {
      "return an error message" in {
        val fileIO = new FileIOJSON
        val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))

        // Schreiben einer fehlerhaften JSON-Datei
        val incorrectJson = Json.obj("game" -> "incorrectFormat")
        val pw = new PrintWriter(new File("game.json"))
        try {
          pw.write(Json.prettyPrint(incorrectJson))
        } finally {
          pw.close()
        }

        // Laden des Spiels aus der fehlerhaften JSON-Datei
        val loadResult = fileIO.load(game)

        // Überprüfen, ob eine Fehlermeldung zurückgegeben wird
        loadResult should include("Fehler beim Laden des Spiels")
      }
    }
    "loading a game with one target word" should {
      "correctly load the game state from JSON" in {
        val fileIO = new FileIOJSON
        val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))

        // Erstellen einer JSON-Datei mit einem Zielwort
        val jsonWithOneTargetWord = Json.parse(
          """
            {
              "game": {
                "mech": {
                  "winningboard": {"1": false},
                  "Versuch": 2
                },
                "board": {
                  "gameboard": [
                    {
                      "key": 1,
                      "gamefield": {
                        "5": "?????", "1": "TISCH", "6": "?????", "2": "?????",
                        "3": "?????", "4": "?????"
                      }
                    }
                  ]
                },
                "mode": {
                  "TargetWord": {"1": "FLUSS"},
                  "limit": 6
                }
              }
            }
          """)

        // Schreiben des JSON-Inhalts in eine Datei
        val pw = new PrintWriter(new File("game.json"))
        try {
          pw.write(Json.prettyPrint(jsonWithOneTargetWord))
        } finally {
          pw.close()
        }

        // Laden des Spiels
        fileIO.load(game)

        // Überprüfen, ob das Spiel korrekt geladen wurde
        game.getGamemech().getN() should be(2)
        game.getGamemode().getLimit() should be(6)
        game.getGamemode().getTargetword() should contain(1 -> "FLUSS")
        game.getGamefield().getMap()(1).getMap() should contain(1 -> "TISCH")
      }
    }

    "gameboardFromJason" should {
      "correctly extract and use keys from JSON" in {
        val fileIO = new FileIOJSON
        val jsonGameBoard = Json.arr(
          Json.obj("key" -> 1, "gamefield" -> Json.obj("1" -> "A")),
          Json.obj("key" -> 2, "gamefield" -> Json.obj("1" -> "B"))
        )

        val result = fileIO.gameboardFromJason(jsonGameBoard.value.toSeq)

        result.keys should contain allOf(1, 2)
      }
    }
    "gameboardFromJason" should {
      "correctly convert gamefield data from JSON" in {
        val fileIO = new FileIOJSON
        val jsonGameBoard = Json.arr(
          Json.obj("key" -> 1, "gamefield" -> Json.obj("1" -> "X", "2" -> "Y"))
        )

        val result = fileIO.gameboardFromJason(jsonGameBoard.value.toSeq)

        result(1) should contain allOf(1 -> "X", 2 -> "Y")
      }
    }
    "gameboardFromJason" should {
      "correctly apply case conversion for gamefield values" in {
        val fileIO = new FileIOJSON
        val jsonGameBoard = Json.arr(
          Json.obj("key" -> 1, "gamefield" -> Json.obj("1" -> "1", "2" -> "2"))
        )

        val result = fileIO.gameboardFromJason(jsonGameBoard.value.toSeq)

        result(1) should contain allOf(1 -> "1", 2 -> "2")
      }
    }

    "gameboardFromJason" should {
      "correctly apply map function to convert gamefield keys to Int" in {
        val fileIO = new FileIOJSON
        val jsonGameBoard = Json.arr(
          Json.obj(
            "key" -> 1,
            "gamefield" -> Json.obj("1" -> "X", "2" -> "Y", "3" -> "Z")
          )
        )

        val result = fileIO.gameboardFromJason(jsonGameBoard.value.toSeq)

        // Überprüfen, ob die Schlüssel im inneren Map als Java-Integer (Scala Int) vorliegen
        all(result(1).keys.map(_.asInstanceOf[AnyRef])) shouldBe a[java.lang.Integer]
      }
    }

    "gameboardFromJason" should {
      "correctly extract and store gamefield values from JSON" in {
        val fileIO = new FileIOJSON
        val jsonGameBoard = Json.arr(
          Json.obj(
            "key" -> 1,
            "gamefield" -> Json.obj("1" -> "A", "2" -> "B", "3" -> "C")
          )
        )

        val result = fileIO.gameboardFromJason(jsonGameBoard.value.toSeq)

        // Überprüfen, ob die Werte im inneren Map korrekt sind
        result(1) should contain allOf(1 -> "A", 2 -> "B", 3 -> "C")
      }
    }

    "loading game mechanics" should {
      "correctly restore winningboard and attempts from JSON" in {
        val fileIO = new FileIOJSON
        val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        val jsonWithMechanics = Json.obj(
          "game" -> Json.obj(
            "mech" -> Json.obj(
              "winningboard" -> Json.obj("1" -> true, "2" -> false),
              "Versuch" -> 3
            ),
            "board" -> Json.obj("gameboard" -> Json.arr()),
            "mode" -> Json.obj("TargetWord" -> Json.obj(), "limit" -> 5)
          )
        )

        val pw = new PrintWriter(new File("game.json"))
        try {
          pw.write(Json.prettyPrint(jsonWithMechanics))
        } finally {
          pw.close()
        }

        fileIO.load(game)
        game.getGamemech().getWinningboard() should contain allOf(1 -> true, 2 -> false)
        game.getGamemech().getN() should be(3)
      }
    }
    /*"loading game with specific winningboard size" should {
      "change game state based on winningboard size" in {
        val fileIO = new FileIOJSON
        val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
        val jsonWithFourElementsWinningBoard = Json.obj(
          "game" -> Json.obj(
            "mech" -> Json.obj("winningboard" -> Json.obj("1" -> false, "2" -> false, "3" -> false, "4" -> false)),
            "board" -> Json.obj("gameboard" -> Json.arr()),
            "mode" -> Json.obj("TargetWord" -> Json.obj("1" -> "WORD1", "2" -> "WORD2", "3" -> "WORD3", "4" -> "WORD4"), "limit" -> 5)
          )
        )

        val pw = new PrintWriter(new File("game.json"))
        try {
          pw.write(Json.prettyPrint(jsonWithFourElementsWinningBoard))
        } finally {
          pw.close()
        }

        fileIO.load(game)

        // Überprüfen der winningboard-Größe nach dem Laden
        val actualWinningBoardSize = game.getGamemech().getWinningboard().size
        val expectedWinningBoardSize = jsonWithFourElementsWinningBoard("game")("mech")("winningboard").as[Map[Int, Boolean]].size
        actualWinningBoardSize should be(expectedWinningBoardSize)

        // Überprüfen der Größe des TargetWords
        game.getGamemode().getTargetword().size shouldBe 4
      }
    }*/



    /*"loading game with specific board and mode" should {
      "loading game with specific board and mode" should {
        "correctly restore gameboard, target words and limit from JSON" in {
          val fileIO = new FileIOJSON
          val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))
          val jsonWithBoardAndMode = Json.obj(
            "game" -> Json.obj(
              "mech" -> Json.obj("winningboard" -> Json.obj("1" -> false)),
              "board" -> Json.obj(
                "gameboard" -> Json.arr(
                  Json.obj("key" -> 1, "gamefield" -> Json.obj("1" -> "A", "2" -> "B"))
                )
              ),
              "mode" -> Json.obj(
                "TargetWord" -> Json.obj("1" -> "APPLE"),
                "limit" -> 6
              )
            )
          )

          val pw = new PrintWriter(new File("game.json"))
          try {
            pw.write(Json.prettyPrint(jsonWithBoardAndMode))
          } finally {
            pw.close()
          }

          fileIO.load(game)
          game.getGamefield().getMap()(1).getMap() should contain allOf(1 -> "A", 2 -> "B")
          game.getGamemode().getTargetword() should contain(1 -> "APPLE")
          game.getGamemode().getLimit() should be(6)

          // Zusätzliche Überprüfungen
          game.getGamemech().getWinningboard() should contain key 1
          game.getGamemech().getN() shouldBe a[Integer]
        }
      }
    }*/


    }
  }

  
