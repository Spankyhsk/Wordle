package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.GameInterface
import de.htwg.se.wordle.model.gamemechComponent.Gamemech
import de.htwg.se.wordle.model.gamefieldComponent.*
import de.htwg.se.wordle.model.gamemodeComponnent.*

import play.api.libs.json._
import scala.io.Source
import de.htwg.se.wordle.model.GameInterface

class FileIOJSON extends FileIOInterface{

  override def load(game:GameInterface):Unit={
    val source:String = Source.fromFile("game.json").getLines().mkString
    val json: JsValue = Json.parse(source)
    
  }

  override def save(game:GameInterface):Unit={
    import java.io._
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }
  
  
  def gameToJson(game:GameInterface):JsValue = {
    Json.obj(
      "game"->Json.obj(
        "mech"-> Json.obj(),
        "board"->Json.obj(),
        "mode"->Json.obj()
        
      )
    )
  }

}
