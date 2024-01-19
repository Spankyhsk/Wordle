import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.*
import de.htwg.se.wordle.model.gamefieldComponent.{GamefieldInterface, gamefield}
import de.htwg.se.wordle.model.gamemechComponent.gamemechInterface
import de.htwg.se.wordle.model.gamemodeComponnent.GamemodeInterface
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.wordle.util.{Observer, Event} // Angenommen, diese Typen befinden sich im util-Paket



class ControllSpec extends AnyWordSpec with Matchers {

  // Stub-Klassen für die Abhängigkeiten
  class StubGameMech extends gamemechInterface {
    var countCalled = false
    var controllLengthCalled = false
    var controllRealWordCalled = false
    var evaluateGuessCalled = false
    var guessTransformCalled = false
    var setWinningBoardCalled = false
    var resetWinningBoardCalled = false
    var compareTargetGuessCalled = false
    var getNCalled = false
    var setNCalled = false
    var buildWinningBoardCalled = false



    var gameOver = false
    var playerWon = false

    override def count(limit: Int): Boolean = {
      countCalled = true
      if (gameOver) false else true
    }

    override def areYouWinningSon(): Boolean = {
      playerWon
    }

    // Methoden, um Test-Szenarien zu setzen
    def setGameOverScenario(): Unit = {
      gameOver = true
    }

    def setWinningScenario(): Unit = {
      playerWon = true
    }


    override def controllLength(n: Int, wordLength: Int): Boolean = {
      controllLengthCalled = true
      true // Ändern Sie diesen Wert bei Bedarf
    }

    override def controllRealWord(guess: String): Boolean = {
      controllRealWordCalled = true
      true // Ändern Sie diesen Wert bei Bedarf
    }

    override def buildwinningboard(n: Int, key: Int): Unit = {
      buildWinningBoardCalled = true // Setze Flagge auf true, wenn Methode aufgerufen wird
    }

    override def setWin(key: Int): Unit = {}

    override def getWin(key: Int): Boolean = false



    override def GuessTransform(guess: String): String = {
      guessTransformCalled = true
      guess.toUpperCase
    }

    override def compareTargetGuess(n: Int, targetWord: Map[Int, String], guess: String): Unit = {
      compareTargetGuessCalled = true
    }

    override def evaluateGuess(targetWord: String, guess: String): String = {
      evaluateGuessCalled = true
      "Feedback" // Ändern Sie diesen Wert bei Bedarf
    }

    override def getN(): Int = {
      getNCalled = true
      0 // Ändern Sie diesen Wert bei Bedarf
    }

    override def setN(zahl: Integer): Unit = {
      setNCalled = true
    }

    override def getWinningboard(): Map[Int, Boolean] = Map()

    override def setWinningboard(wBoard: Map[Int, Boolean]): Unit = {
      setWinningBoardCalled = true
    }

    override def resetWinningBoard(size: Int): Unit = {
      resetWinningBoardCalled = true
    }
  }

  class StubGameField extends GamefieldInterface[GamefieldInterface[String]] {
    var buildGameboardCalled = false
    var resetCalled = false

    override def set(key: Int, feedback: String): Unit = {}

    override def buildGamefield(n: Int, key: Int, value: String): Unit = {}

    override def buildGameboard(n: Int, key: Int): Unit = {
      buildGameboardCalled = true
    }

    override def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit = {}

    override def getMap(): Map[Int, GamefieldInterface[String]] = {
      Map(1 -> new gamefield()) // Stellen Sie sicher, dass der Schlüssel 1 vorhanden ist
    }

    override def reset(): Unit = {
      resetCalled = true
    }

    override def setMap(boardmap: Map[Int, Map[Int, String]]): Unit = {}

    override def toString: String = "Gamefield"
  }


  class StubGameMode extends GamemodeInterface {
    var changeStateCalled = false
    var getTargetwordCalled = false

    override def getTargetword(): Map[Int, String] = {
      getTargetwordCalled = true
      Map(1 -> "Word")
    }

    override def getLimit(): Int = 0

    override def getWordList(): Array[String] = Array()

    override def setTargetWord(targetWordMap: Map[Int, String]): Unit = {}

    override def setLimit(Limit: Int): Unit = {}

    override def toString(): String = "Gamemode"
  }

  class TestObserver extends Observer {
    var lastEvent: Option[Event] = None

    override def update(e: Event): Unit = {
      lastEvent = Some(e)
    }
  }



  // Tests
  "A Controll object" should {
    val stubGameMech = new StubGameMech
    val stubGameField = new StubGameField
    val stubGameMode = new StubGameMode
    val game = new Game(stubGameMech, stubGameField, stubGameMode)
    val controllInstance = controll(game, null)

    "count calls GameMech's count" in {
      controllInstance.count() shouldBe true
      stubGameMech.countCalled shouldBe true
    }

    "controllLength delegates to GameMech's controllLength" in {
      controllInstance.controllLength(5) shouldBe true
      stubGameMech.controllLengthCalled shouldBe true
    }

    "controllRealWord delegates to GameMech's controllRealWord" in {
      controllInstance.controllRealWord("test") shouldBe true
      stubGameMech.controllRealWordCalled shouldBe true
    }

    "evaluateGuess delegates to GameMech's evaluateGuess" in {
      controllInstance.evaluateGuess("test")
      stubGameMech.evaluateGuessCalled shouldBe true
    }


    "GuessTransform delegates to GameMech's GuessTransform" in {
      controllInstance.GuessTransform("test") shouldBe "TEST"
      stubGameMech.guessTransformCalled shouldBe true
    }

    "setVersuche sets number of tries in GameMech" in {
      controllInstance.setVersuche(5)
      stubGameMech.setNCalled shouldBe true
    }

    "getVersuche retrieves number of tries from GameMech" in {
      controllInstance.getVersuche() shouldBe 0
      stubGameMech.getNCalled shouldBe true
    }

    "areYouWinningSon delegates to GameMech's areYouWinningSon" in {
      controllInstance.areYouWinningSon("guess") shouldBe false
      stubGameMech.compareTargetGuessCalled shouldBe true
    }

    "createwinningboard delegates to GameMech's buildwinningboard" in {
      controllInstance.createwinningboard()
      stubGameMech.buildWinningBoardCalled shouldBe true // Überprüfe, ob die Flagge gesetzt wurde
    }


    "createGameboard delegates to Gamefield's buildGameboard" in {
      controllInstance.createGameboard()
      stubGameField.buildGameboardCalled shouldBe true
    }

    "changeState delegates to Gamemode's changeState" in {
      controllInstance.changeState(2)
      //stubGameMode.changeStateCalled shouldBe true
      stubGameMech.resetWinningBoardCalled shouldBe true
      stubGameField.resetCalled shouldBe true
    }

    "getTargetword retrieves target word from Gamemode" in {
      controllInstance.getTargetword().toString should include("1 ->")
      stubGameMode.getTargetwordCalled shouldBe true
    }

    "TargetwordToString retrieves string representation of target word from Gamemode" in {
      controllInstance.TargetwordToString() should include("Wort1:")
    }

    "count method" should {
      "notify observers with LOSE event when the game is over" in {
        val stubGameMech = new StubGameMech
        val stubGameField = new StubGameField
        val stubGameMode = new StubGameMode
        val game = new Game(stubGameMech, stubGameField, stubGameMode)
        val testObserver = new TestObserver
        val controllInstance = new controll(game, null)
        controllInstance.add(testObserver)

        stubGameMech.setGameOverScenario()
        controllInstance.count() shouldBe false
        testObserver.lastEvent shouldBe Some(Event.LOSE)
      }
    }

    "areYouWinningSon method" should {
      "notify observers with WIN event when the player wins" in {
        val stubGameMech = new StubGameMech
        val stubGameField = new StubGameField
        val stubGameMode = new StubGameMode
        val game = new Game(stubGameMech, stubGameField, stubGameMode)
        val testObserver = new TestObserver
        val controllInstance = new controll(game, null)
        controllInstance.add(testObserver)
        stubGameMech.setWinningScenario()
        controllInstance.areYouWinningSon("someGuess") shouldBe true
        testObserver.lastEvent shouldBe Some(Event.WIN)
      }
    }


    "apply method" should {
      "create a new Controll instance with the specified configuration" in {
        val xmlControl = controll.apply("XML")
        xmlControl shouldBe a[controll]

        val jsonControl = controll.apply("JSON")
        jsonControl shouldBe a[controll]
      }
    }



    "toString method" should {
      "return the string representation from the Game object" in {
        val expectedStringRepresentation = "Game Representation"
        val stubGameMech = new StubGameMech
        val stubGameField = new StubGameField
        val stubGameMode = new StubGameMode

        // Stub für Game, um eine vordefinierte Zeichenkette zurückzugeben
        class StubGame extends Game(stubGameMech, stubGameField, stubGameMode) {
          override def toString: String = expectedStringRepresentation
        }

        val stubGame = new StubGame
        val controllInstance = new controll(stubGame, null)

        controllInstance.toString shouldBe expectedStringRepresentation
      }
    }


  }



}
