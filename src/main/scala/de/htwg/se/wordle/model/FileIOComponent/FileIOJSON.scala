package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.GameInterface
import de.htwg.se.wordle.model.gamemechComponent.gamemechInterface
import de.htwg.se.wordle.model.gamefieldComponent.GamefieldInterface
import de.htwg.se.wordle.model.gamemodeComponnent.GamemodeInterface

import play.api.libs.json._
import scala.io.Source
import de.htwg.se.wordle.model.GameInterface

class FileIOJSON extends FileIOInterface{

  override def load(game: GameInterface): String = {
    try {
      val source: String = scala.io.Source.fromFile("game.json").getLines().mkString
      val json: JsValue = Json.parse(source)

      implicit val intKeyReads: Reads[Int] = Reads.IntReads.map(_.toInt)

      val winningBoard: Map[Int, Boolean] = (json \ "game" \ "mech" \ "winningboard").as[Map[Int, Boolean]]
      val versuch = (json \ "game" \ "mech" \ "Versuch").as[Int]

      winningBoard.size match {
        case 1 =>
          game.changeState(1)
        case 2 =>
          game.changeState(2)
        case 4 =>
          game.changeState(3)
      }
      game.setWinningboard(winningBoard)
      game.setN(versuch)

      val gameBoard: Seq[JsValue] = (json \ "game" \ "board" \ "gameboard").as[Seq[JsValue]]

      game.setMap(gameboardFromJason(gameBoard))

      val targetWord: Map[Int, String] = (json \ "game" \ "mode" \ "TargetWord").as[Map[Int, String]]
      val limit: Int = (json \ "game" \ "mode" \ "limit").as[Int]

      game.setTargetWord(targetWord)
      game.setLimit(limit)

      s"Laden des Spiels game.json war erfolgreich"
    } catch {
      case e: Exception =>
        s"Fehler beim Laden des Spiels: ${e.getMessage}"
    }
  }

  override def save(game:GameInterface):Unit={
    import java.io._
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }
  
  
  def gameToJson(game:GameInterface):JsObject = {
    val mech= game.getGamemech()
    val board = game.getGamefield()
    val mode = game.getGamemode()
    Json.obj(
      "game"->Json.obj(
        "mech"-> Json.obj(
      "winningboard"-> mech.getWinningboard(),
          "Versuch"-> mech.getN()
        ),
        "board" -> gameboardToJason(board.getMap()),
        "mode"->Json.obj(
          "TargetWord"->mode.getTargetword(),
          "limit"->mode.getLimit()
        )
        )

      )

  }

  def gameboardToJason(map:Map[Int, GamefieldInterface[String]]):JsObject={
    Json.obj(
      "gameboard"-> Json.toJson(
        for{
          key <- 1 until map.size+1
        }yield {
          Json.obj(
            "key"->key,
            "gamefield"->map(key).getMap()
          )
        }
      )
    )
  }
  
  def gameboardFromJason(seq:Seq[JsValue]):Map[Int, Map[Int, String]]={
    val resultMap:Map[Int, Map[Int, String]] = seq.map{ jsValue =>
      val key = (jsValue\"key").as[Int]
      val gameField = (jsValue\"gamefield").as[Map[String, String]].map{
        case (k,v) => k.toInt -> v
      }
      key -> gameField
    }.toMap

    resultMap
  }

}
