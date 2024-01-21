package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model
import de.htwg.se.wordle.model.GameInterface
import de.htwg.se.wordle.model.*
import de.htwg.se.wordle.model.gamefieldComponent.GamefieldInterface
import de.htwg.se.wordle.model.gamemechComponent.gamemechInterface
import de.htwg.se.wordle.model.gamemodeComponnent.GamemodeInterface

import scala.xml.*

class FileIOXML extends FileIOInterface {
  override def load(game:GameInterface):String={
    try {
      val file = scala.xml.XML.loadFile("game.xml")

      val winningboard: Seq[(Int, Boolean)] = (file \\ "game" \ "Mech" \ "Gamemech" \ "winningboard" \ "entry").map { entry =>
        val key = (entry \ "@key").text.toInt
        val value = (entry \ "@value").text.toBoolean
        key -> value
      }
      val anzahl = (file \\ "game" \ "Mech" \ "Gamemech" \ "anzahl").text.trim.toInt

      val winboard = winningboard.toMap
      winboard.size match {
        case 1 =>
          game.changeState(1)
        case 2 =>
          game.changeState(2)
        case 4 =>
          game.changeState(3)

      }
      game.setWinningboard(winboard)
      game.setN(anzahl)

      val gameboard: Seq[(Int, Map[Int, String])] = (file \\ "game" \ "board" \ "gameboard" \ "entry").map { entry =>
        val key = (entry \ "@key").text.toInt
        val value = InnerMapFromXML(entry)
        key -> value
      }
      game.setMap(gameboard.toMap)

      val targetword: Seq[(Int, String)] = (file \\ "game" \ "mode" \ "Gamemode" \ "TargetWord" \ "entry").map { entry =>
        val key = (entry \ "@key").text.toInt
        val value = (entry \ "@value").text
        key -> value
      }
      game.setTargetWord(targetword.toMap)

      val limit = (file \\ "game" \ "mode" \ "Gamemode" \ "limit").text.trim.toInt
      game.setLimit(limit)
      
      s"Laden des Spiels game.xml war erfolgreich"
    } catch {
      case e: Exception =>
        s"Fehler beim Laden des Spiels: ${e.getMessage}"
    }
  }
  override def save(game:GameInterface)={
    import java.io._
    val pw = new PrintWriter(new File("game.xml"))
    val prettyPrinter = new PrettyPrinter(120,4)
    val xml = prettyPrinter.format(gameToXML(game))
    pw.write(xml)
    pw.close()
  }
  def gameToXML(game:GameInterface):xml.Node={
    <game>{
      <Mech>{
        GameMechToXML(game.getGamemech())
        }
      </Mech>
      <board>{
        GameboardToXML(game.getGamefield())
        }</board>
      <mode>{
        GamemodeTOXML(game.getGamemode())
        }</mode>
      }</game>

  }
  def GameMechToXML(mech:gamemechInterface):xml.Elem={
    <Gamemech>{
      <winningboard>
        {mech.getWinningboard().map{case(key,value) => <entry key={key.toString} value={value.toString}/>}
        }
      </winningboard>
      <anzahl>
        {mech.getN()}
      </anzahl>
      }</Gamemech>
  }
  def GameboardToXML(board:GamefieldInterface[GamefieldInterface[String]]): xml.Elem={
    <gameboard>
      {board.getMap().map{case(key, value)=> <entry key={key.toString}>{InnerMapToXML(value.getMap())}</entry>}}
    </gameboard>
  }
  def InnerMapToXML(gamefield:Map[Int, String]): Elem ={
    <Gamefield>{
      gamefield.map{case(key, value)=> <Innerentry key={key.toString} value={value.toString}/>}
      }
    </Gamefield>
  }
  def InnerMapFromXML(xml:NodeSeq):Map[Int, String]={
    val gamefield:Seq[(Int, String)] = (xml\"Gamefield"\"Innerentry").map{ entry =>
      val key = (entry\"@key").text.toInt
      val value = (entry\"@value").text
      key -> value
    }
    gamefield.toMap
  }
  def GamemodeTOXML(mode:GamemodeInterface):xml.Elem={
    <Gamemode>{
      <TargetWord>{
        mode.getTargetword().map{case(key, value)=> <entry key={key.toString} value={value.toString}/>}
        }</TargetWord>
      <limit>{
        mode.getLimit()
        }</limit>
      }</Gamemode>
  }

}
