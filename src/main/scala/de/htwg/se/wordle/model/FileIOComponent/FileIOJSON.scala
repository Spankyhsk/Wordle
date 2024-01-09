/*package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.GameInterface
import de.htwg.se.wordle.model.gamemechComponent.*
import de.htwg.se.wordle.model.gamefieldComponent.*
import de.htwg.se.wordle.model.gamemodeComponnent.*

import play.api.libs.json._
import scala.io.Source

class FileIOJSON extends FileIOInterface{

  override def load():GameInterface={
    var game: GameInterface =null
    val source:String = Source.fromFile("game.json").getLines().mkString
    val json: JsValue = Json.parse(source)
    game
  }

  override def save(game:GameInterface):Unit={
    import java.io._
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }
  
  implicit val GameMechWrites = new Writes[gamemechInterface]{
    def writes(mech:gamemechInterface)=Json.obj(
      "winningBoard"-> mech.getWinningboard(),
      "n"->mech.getN()
    )
  }

  def gameToJson(game:GameInterface):JsValue = {
    Json.obj(
      "game"->Json.obj(
        "mech"-> Json.obj(game.getGamemech()),
        "board"->Json.obj(),
        "mode"->Json.obj()
        
      )
    )
  }

}*/
