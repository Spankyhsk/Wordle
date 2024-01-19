package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.Game
import de.htwg.se.wordle.model.gamefieldComponent.gameboard
import de.htwg.se.wordle.model.gamemechComponent.GameMech
import de.htwg.se.wordle.model.gamemodeComponnent.gamemode
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsObject

class FileIOJsonSpec extends AnyWordSpec with Matchers {
  "FileIOJSON" when {
    "FileIOJSON" should {


      "load retrun a success message on successful loading" in {
        val game = new Game(new GameMech(), new gameboard(), gamemode(1))
        val file = new FileIOJSON
        file.save(game)
        val result = file.load(game)

        result should be("Laden des Spiels game.json war erfolgreich")
      }


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
  }
}
  
