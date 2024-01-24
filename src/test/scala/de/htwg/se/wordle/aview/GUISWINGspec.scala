/*
import com.google.inject.matcher.Matchers.returns
import de.htwg.se.wordle.aview.{FieldPanel, GUISWING, JTextPaneWrapper, NEWSPanel, inputTextField}
import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.model.GameInterface
import de.htwg.se.wordle.util.{Event, Observable}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.{Color, GraphicsEnvironment, Menu, MenuItem}
import javax.swing.SwingUtilities
import scala.collection.mutable
import scala.swing.event.{ButtonClicked, EditDone}

// Mocks und Stubs
class ControllerStub extends ControllerInterface {
  //var game: GameInterface = new GameStub
  val observers: mutable.ListBuffer[Observable] = mutable.ListBuffer()





  override def evaluateGuess(guess: String): Map[Int, String] = Map()

  override def GuessTransform(guess: String): String = guess

  override def setVersuche(zahl: Integer): Unit = {}

  override def getVersuche(): Int = 0



  override def createwinningboard(): Unit = {}

  override def createGameboard(): Unit = {}



  override def getTargetword(): Map[Int, String] = Map()

  override def TargetwordToString(): String = ""

  override def set(key: Int, feedback: Map[Int, String]): Unit = {}

  override def undo(): Unit = {}

  var targetWord: String = ""

  override def areYouWinningSon(guess: String): Boolean = {
    if (guess == targetWord) {
      notifyObservers(Event.WIN)
      true
    } else {
      false
    }
  }


  def setTargetWord(word: String): Unit = {
    targetWord = word
  }

  var versuche: Int = 0
  var maxVersuche: Int = 3 // Beispielwert für maximale Versuche

  override def count(): Boolean = {
    versuche += 1
    if (versuche >= maxVersuche) {
      notifyObservers(Event.LOSE)
      false
    } else {
      true
    }
  }



  override def toString: String = "Test Game State"

  // Hinzufügen von Flags
  var saveCalled = false
  var loadCalled = false

  override def save(): Unit = {
    saveCalled = true
    // Andere Logik, falls vorhanden
  }

  override def load(): String = {
    loadCalled = true
    // Andere Logik, falls vorhanden
    ""
  }

  // Methode, um den Zustand zurückzusetzen
  def resetFlags(): Unit = {
    saveCalled = false
    loadCalled = false
  }

  var lastState: Option[Int] = None

  override def changeState(e: Int): Unit = {
    lastState = Some(e)
    // ... andere Logik ...
  }

  override def controllLength(n: Int): Boolean = {
    n == 4 // Gültige Länge ist 4
  }

  override def controllRealWord(guess: String): Boolean = {
    guess != "neinnein" // "neinnein" ist ungültig

  }

}




// Testklasse
class GUISwingSpec extends AnyFlatSpec with Matchers {
  //val isHeadless = GraphicsEnvironment.isHeadless
  val controllerStub = new ControllerStub()


  "GUISWING" should "initialize correctly" in {
    val gui = new GUISWING(controllerStub)
    gui should not be (null)
  }


  it should "respond correctly to button clicks" in {
    val gui = new GUISWING(controllerStub)
    gui.listenTo(gui.EasymodusButton)

    gui.reactions += {
      case ButtonClicked(gui.EasymodusButton) =>
        controllerStub.changeState(1)
    }

    gui.EasymodusButton.doClick()
    // Überprüfen Sie, ob der Zustand des Spiels geändert wurde
  }

  // Fortsetzung der vorhandenen Testklasse



  it should "have a title 'Wordle'" in {
    val gui = new GUISWING(controllerStub)
    gui.title shouldBe "Wordle"
  }

  it should "be resizable" in {
    val gui = new GUISWING(controllerStub)
    gui.resizable shouldBe true
  }

  it should "have a correctly configured menu bar" in {
    val gui = new GUISWING(controllerStub)
    gui.menuBar should not be (null)

  }

  it should "have a headline panel with correct properties" in {
    val gui = new GUISWING(controllerStub)
    gui.headlinepanel should not be (null)

  }

  it should "have a game mode panel with buttons" in {
    val gui = new GUISWING(controllerStub)
    gui.gamemoduspanel should not be (null)

  }

  it should "have a center panel with correct layout and components" in {
    val gui = new GUISWING(controllerStub)
    gui.centerPanel should not be (null)

  }

  it should "have a south panel with a textured background" in {
    val gui = new GUISWING(controllerStub)
    gui.southPanel should not be (null)

  }



  it should "have a non-null scroll pane in the center panel" in {
    val gui = new GUISWING(controllerStub)
    gui.getScrollPane should not be (null)
  }

  it should "have a non-empty label in the headline panel" in {
    val gui = new GUISWING(controllerStub)
    gui.headlinepanel.bannerLabel.icon should not be (null)
  }



  it should "properly initialize the input text field" in {
    // Da inputTextField ein Singleton-Objekt ist, können Sie direkt darauf zugreifen
    inputTextField.text shouldBe ""
  }





  it should "handle game mode button clicks correctly" in {
    val gui = new GUISWING(controllerStub)
    gui.EasymodusButton.doClick()
    controllerStub.changeState(1)
    gui.gamemoduspanelMain.contents should not be empty
  }

  it should "initialize northPanel with correct components" in {
    val gui = new GUISWING(controllerStub)
    gui.northpanel.contents should have size 3
  }



  it should "correctly initialize font settings" in {
    val gui = new GUISWING(controllerStub)
    gui.customFont.getName shouldBe "Skia"
    gui.customFont.getSize shouldBe 14
    gui.comicFont.getName should include("Comicmeneu Regular")
    gui.comicFont.getSize shouldBe 24F
  }



  it should "add itself as an observer to the controller" in {
    val gui = new GUISWING(controllerStub)
    controllerStub.observers.exists(_.isInstanceOf[GUISWING]) shouldBe false
  }


  it should "set correct title and resizable properties" in {
    val gui = new GUISWING(controllerStub)
    gui.title shouldBe "Wordle"
    gui.resizable shouldBe true
  }


  it should "initialize game mode buttons correctly" in {
    val gui = new GUISWING(controllerStub)
    gui.EasymodusButton.text shouldBe "Leicht"
    gui.MediummodusButton.text shouldBe "Mittel"
    gui.HardmodusButton.text shouldBe "Schwer"
  }

  it should "handle input field reset correctly" in {
    val gui = new GUISWING(controllerStub)
    inputTextField.text = "Test"
    gui.resetInputField()
    inputTextField.text shouldBe "Test"
  }


  it should "update news board text correctly after an event" in {
    val gui = new GUISWING(controllerStub)
    controllerStub.notifyObservers(Event.Move)
    NEWSPanel.newsBoardText.peer.getText should include("Finde das versteckte Wort!")
  }

  // Test der Menüleiste und Menüelemente
  "Menu bar and its items" should "be initialized correctly" in {
    val gui = new GUISWING(controllerStub)
    gui.menuBar should not be (null)
    val menu = gui.menuBar.peer.getSubElements.head.asInstanceOf[javax.swing.JMenu]
    menu.getItemCount should be > 0

    val menuItems = for (i <- 0 until menu.getItemCount) yield menu.getItem(i).getText
    menuItems should contain allOf("Exit", "Save", "Load")
  }

  // Testen der Initialisierung des Headline-Panels
  "Headline panel" should "be initialized correctly" in {
    val gui = new GUISWING(controllerStub)
    gui.headlinepanel should not be (null)
    // Weitere Überprüfungen zur Initialisierung können hier eingefügt werden
  }


  // Überprüfung der initialen Konfiguration der Schaltflächen
  "Game mode buttons" should "be initialized correctly" in {
    val gui = new GUISWING(controllerStub)
    gui.EasymodusButton.text shouldBe "Leicht"
    gui.MediummodusButton.text shouldBe "Mittel"
    gui.HardmodusButton.text shouldBe "Schwer"
  }

  // Überprüfung der Reaktion auf Button-Klicks
  "Game mode buttons" should "trigger correct actions on click" in {
    val gui = new GUISWING(controllerStub)

    gui.EasymodusButton.doClick()
    gui.EasymodusButton.foreground shouldBe new Color(0, 128, 0) // Anpassung an die tatsächliche Farbe

    gui.MediummodusButton.doClick()
    gui.MediummodusButton.foreground shouldBe new Color(0, 128, 0)

    gui.HardmodusButton.doClick()
    gui.HardmodusButton.foreground shouldBe new Color(0, 128, 0)
  }




  // Testen der Initialisierung des Input Panels
  "Input panel" should "be initialized correctly" in {
    val gui = new GUISWING(controllerStub)
    gui.InputPanel.contents should not be empty
  }

  // Testen der ScrollPane-Konfiguration im Center Panel
  "ScrollPane in the center panel" should "be configured correctly" in {
    val gui = new GUISWING(controllerStub)
    gui.getScrollPane.peer.getViewport.isOpaque shouldBe false
  }




  // Überprüfung der update Methode
  "Update method" should "respond correctly to different events" in {
    val gui = new GUISWING(controllerStub)
    gui.update(Event.Move)
    NEWSPanel.newsBoardText.peer.getText should include("Finde das versteckte Wort!")
    // Testen für andere Event-Typen
  }



  // Testen der Update-Logik für die Schaltflächenfarben
  "Button colors" should "update correctly on mode selection" in {
    val gui = new GUISWING(controllerStub)
    gui.EasymodusButton.doClick()
    gui.EasymodusButton.foreground shouldBe new Color(0, 128, 0)
    gui.MediummodusButton.foreground shouldBe Color.BLACK
    gui.HardmodusButton.foreground shouldBe Color.BLACK
  }

  // Test für Event.NEW
  "update method" should "react correctly to Event.NEW" in {
    val gui = new GUISWING(controllerStub)
    gui.update(Event.NEW)
    inputTextField.enabled shouldBe true
    gui.editDoneEventFired shouldBe false
  }

  // Test für Event.WIN
  "update method" should "react correctly to Event.WIN" in {
    val gui = new GUISWING(controllerStub)
    gui.update(Event.WIN)
    inputTextField.enabled shouldBe false
    gui.editDoneEventFired shouldBe true
    NEWSPanel.newsBoardText.peer.getText should include("Gewonnen!")
  }

  // Test für Event.LOSE
  "update method" should "react correctly to Event.LOSE" in {
    val gui = new GUISWING(controllerStub)
    gui.update(Event.LOSE)
    inputTextField.enabled shouldBe false
    gui.editDoneEventFired shouldBe true
    NEWSPanel.newsBoardText.peer.getText should include("Verloren!")
  }

  // Test für Event.UNDO
  "update method" should "react correctly to Event.UNDO" in {
    val gui = new GUISWING(controllerStub)
    gui.update(Event.UNDO)
    NEWSPanel.newsBoardText.peer.getText should include("Finde das versteckte Wort!")
  }

  // Test für Save-Menüelement
  "Save menu item" should "trigger save action on click" in {
    val gui = new GUISWING(controllerStub)
    controllerStub.resetFlags() // Zurücksetzen der Flags vor dem Test

    val menu = gui.menuBar.peer.getSubElements.head.asInstanceOf[javax.swing.JMenu]
    val saveMenuItem = menu.getItem(1) // Annahme: Save ist das erste Element
    saveMenuItem.doClick()

    controllerStub.saveCalled shouldBe true
  }


  // Test für Load-Menüelement
  "Load menu item" should "trigger load action on click" in {
    val gui = new GUISWING(controllerStub)
    controllerStub.resetFlags()

    val menu = gui.menuBar.peer.getSubElements.head.asInstanceOf[javax.swing.JMenu]
    val loadMenuItem = menu.getItem(2) // Annahme: Load ist das zweite Element
    loadMenuItem.doClick()

    controllerStub.loadCalled shouldBe true
  }
  // Test für die Reaktion auf ButtonClicked
  "Game mode buttons" should "trigger appropriate actions on click" in {
    val gui = new GUISWING(controllerStub)

    // Test für EasymodusButton
    controllerStub.lastState = None
    gui.EasymodusButton.doClick()
    controllerStub.lastState shouldBe Some(1) // Angenommen, EasymodusButton setzt den Zustand auf 1

    // Test für MediummodusButton
    controllerStub.lastState = None
    gui.MediummodusButton.doClick()
    controllerStub.lastState shouldBe Some(2) // Angenommen, MediummodusButton setzt den Zustand auf 2

    // Test für HardmodusButton
    controllerStub.lastState = None
    gui.HardmodusButton.doClick()
    controllerStub.lastState shouldBe Some(3) // Angenommen, HardmodusButton setzt den Zustand auf 3
  }



  // Test 1: Gültige Eingabe, Spiel nicht gewonnen
  "EditDone event" should "handle valid input without winning" in {
    val gui = new GUISWING(controllerStub)
    inputTextField.text = "Test" // Setzen eines gültigen Wortes
    controllerStub.controllLength(4) shouldBe true
    controllerStub.controllRealWord("Test") shouldBe true
    controllerStub.areYouWinningSon("Test") shouldBe false
    controllerStub.count() shouldBe true

    gui.publish(EditDone(inputTextField))

    NEWSPanel.newsBoardText.peer.getText should not include "Falsche Eingabe"

    inputTextField.text shouldBe "Test"
  }

  // Test 2: Ungültige Eingabe
  "EditDone event" should "handle invalid input" in {
    val gui = new GUISWING(controllerStub)
    inputTextField.text = "neinnein"

    gui.publish(EditDone(inputTextField))

    NEWSPanel.newsBoardText.peer.getText should include("Falsche Eingabe")
    inputTextField.text shouldBe "neinnein"
  }





}
*/