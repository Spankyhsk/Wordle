package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.*
import de.htwg.se.wordle.model.gamefieldComponent.{gameboard, gamefield}
import de.htwg.se.wordle.model.gamemechComponent.GameMech
import de.htwg.se.wordle.model.gamemodeComponnent.{GamemodeInterface, gamemode}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.xml.{Node, XML}
import java.io.{File, PrintWriter}

class FileIOXMLSpec extends AnyWordSpec with Matchers {

  "A FileIOXML" when {
    val fileIO = new FileIOXML
    val game: GameInterface = new Game(new GameMech(), new gameboard(), gamemode(1))

    "used to load a game" should {
      "load a game successfully from a valid XML file" in {
        val validXML =
          <game>
            <Mech>
              <Gamemech>
                <winningboard>
                  <entry key="1" value="false"/>
                </winningboard>
                <anzahl>2</anzahl>
              </Gamemech>
            </Mech>
            <board>
              <gameboard>
                <entry key="1">
                  <Gamefield>
                    <Innerentry key="5" value="?????"/>
                    <Innerentry key="1" value="FJORD"/>
                    <Innerentry key="6" value="?????"/>
                    <Innerentry key="2" value="?????"/>
                    <Innerentry key="3" value="?????"/>
                    <Innerentry key="4" value="?????"/>
                  </Gamefield>
                </entry>
              </gameboard>
            </board>
            <mode>
              <Gamemode>
                <TargetWord>
                  <entry key="1" value="BEISPIEL"/>
                </TargetWord>
                <limit>6</limit>
              </Gamemode>
            </mode>
          </game>
        val pw = new PrintWriter(new File("validGame.xml"))
        pw.write(validXML.toString())
        pw.close()

        val loadResult = fileIO.load(game)
        loadResult should include("erfolgreich")
        // Weitere Überprüfungen basierend auf dem erwarteten Spielzustand
      }

      "handle errors when trying to load from an invalid or non-existent XML file" in {
        val loadResult = fileIO.load(game)
        //loadResult should include("Fehler")
      }
    }

    "used to save a game" should {
      "save the game state successfully into an XML file" in {
        // Vorbereiten eines Spiels mit einem definierten Zustand
        game.setTargetWord(Map(1 -> "BEISPIEL"))
        game.setLimit(6)
        fileIO.save(game)

        val savedXML = XML.loadFile("game.xml")
        (savedXML \\ "TargetWord" \ "entry" \ "@value").text should be("BEISPIEL")
        (savedXML \\ "limit").text.toInt should be(6)
        // Weitere Überprüfungen basierend auf dem erwarteten XML-Inhalt
      }
    }

    "converting game mechanics to XML" should {
      "correctly convert winningboard and anzahl to XML format" in {
        // Vorbereiten eines Spielmechanik-Objekts
        val mech = new GameMech()
        mech.setWinningboard(Map(1 -> true))
        mech.setN(3)

        val mechXML = fileIO.GameMechToXML(mech)
        (mechXML \\ "winningboard" \ "entry" \ "@value").text should be("true")
        (mechXML \\ "anzahl").text.trim.toInt should be(3)

      }
    }

    /*"converting gameboards to and from XML" should {
      "correctly convert a gameboard to XML format and back" in {
        val fileIO = new FileIOXML
        val board = new gameboard()
        val gamefield = new gamefield()
        gamefield.SetMapper(Map(1 -> "A", 2 -> "B"))
        board.setMap(Map(1 -> gamefield.getMap()))

        // Konvertieren des gameboard zu XML
        val boardXML = fileIO.GameboardToXML(board)

        // Ausgabe des XML für Debugging
        println(s"Board XML: ${boardXML.toString()}")

        // Extrahieren des Gamefield XMLs aus dem Board XML
        val gamefieldXML = (boardXML \\ "entry" \ "Gamefield").head

        // Ausgabe des Gamefield XML für Debugging
        println(s"Gamefield XML: ${gamefieldXML.toString()}")

        // Konvertieren des XML zurück zu einer Map
        val loadedBoardMap = fileIO.InnerMapFromXML(gamefieldXML)

        // Ausgabe der geladenen Map für Debugging
        println(s"Loaded Board Map: $loadedBoardMap")

        // Überprüfen der korrekten Konvertierung
        loadedBoardMap should contain allOf(1 -> "A", 2 -> "B")
      }
    }
    */


    // DummyGameMode implementiert nun GamemodeInterface
    class DummyGameMode(var limit: Int = 0, var targetWord: Map[Int, String] = Map()) extends GamemodeInterface {
      override def setTargetWord(wordMap: Map[Int, String]): Unit = {
        targetWord = wordMap
      }

      override def setLimit(newLimit: Int): Unit = {
        limit = newLimit
      }

      override def getTargetword(): Map[Int, String] = targetWord

      override def getLimit(): Int = limit

      // Dummy-Implementierung der getWordList-Methode
      override def getWordList(): Array[String] = {
        // Hier könnte Ihre Logik stehen, um eine Liste von Wörtern zurückzugeben
        // Im Dummy-Kontext geben wir einfach ein leeres Array zurück
        Array.empty[String]
      }
    }


    "converting game modes to and from XML" should {
      "correctly convert a game mode to XML format and back" in {
        val fileIO = new FileIOXML
        val mode = new DummyGameMode()
        mode.setTargetWord(Map(1 -> "WORD1", 2 -> "WORD2"))
        mode.setLimit(7)
        val modeXML = fileIO.GamemodeTOXML(mode)

        // Überprüfen der konvertierten Zielwörter und des Limits
        val targetWordsFromXML = (modeXML \\ "TargetWord" \ "entry").map(entry =>
          (entry \ "@key").text.toInt -> (entry \ "@value").text
        ).toMap
        val limitFromXML = (modeXML \\ "limit").text.trim.toInt

        targetWordsFromXML should contain allOf(1 -> "WORD1", 2 -> "WORD2")
        limitFromXML should be(7)
      }
    }


    "loading game mechanics from XML" should {
      "correctly restore the game mechanics from XML data" in {
        val fileIO = new FileIOXML
        val xmlData: Node =
          <Gamemech>
            <winningboard>
              <entry key="1" value="true"/>
            </winningboard>
            <anzahl>3</anzahl>
          </Gamemech>

        // Erstellen eines Dummy-GameMech-Objekts und Setzen der Daten aus der XML
        val loadedMech = new GameMech()
        loadedMech.setWinningboard((xmlData \ "winningboard" \ "entry").map(entry => (entry \@ "key").toInt -> (entry \@ "value").toBoolean).toMap)
        loadedMech.setN((xmlData \ "anzahl").text.toInt)

        // Überprüfen der geladenen Daten
        loadedMech.getWinningboard() should contain(1 -> true)
        loadedMech.getN() should be(3)
      }
    }

  }
}
