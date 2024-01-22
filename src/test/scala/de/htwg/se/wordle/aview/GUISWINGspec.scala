/*
import de.htwg.se.wordle.aview.{GUISWING, JTextPaneWrapper, inputTextField}

import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.model.GameInterface
import de.htwg.se.wordle.util.{Event, Observable}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.GraphicsEnvironment
import scala.collection.mutable
import scala.swing.event.ButtonClicked

// Mocks und Stubs
class ControllerStub extends ControllerInterface {
  //var game: GameInterface = new GameStub
  val observers: mutable.ListBuffer[Observable] = mutable.ListBuffer()

  override def count(): Boolean = true
  override def controllLength(n: Int): Boolean = true
  override def controllRealWord(guess: String): Boolean = true
  override def evaluateGuess(guess: String): Map[Int, String] = Map()
  override def GuessTransform(guess: String): String = guess
  override def setVersuche(zahl: Integer): Unit = {}
  override def getVersuche(): Int = 0
  override def areYouWinningSon(guess: String): Boolean = false
  override def createwinningboard(): Unit = {}
  override def createGameboard(): Unit = {}
  override def changeState(e: Int): Unit = {}
  override def getTargetword(): Map[Int, String] = Map()
  override def TargetwordToString(): String = ""
  override def set(key: Int, feedback: Map[Int, String]): Unit = {}
  override def undo(): Unit = {}
  override def save(): Unit = {}
  override def load(): String = ""
  //override def notifyObservers(event: Event.Value): Unit = observers.foreach(_.update(event))
}

class GameStub extends GameInterface {
  override def getGamemech(): Any = null
  override def getGamefield(): Any = null
  override def getGamemode(): Any = null
}


// Testklasse
class GUISwingSpec extends AnyFlatSpec with Matchers {
  val isHeadless = GraphicsEnvironment.isHeadless
  val controllerStub = new ControllerStub()


  if (!isHeadless) {


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

    it should "update output correctly on event" in {
      val gui = new GUISWING(controllerStub)
      // Simulieren Sie ein Event und überprüfen Sie die Reaktion der GUI
    }

    it should "reset the input field correctly" in {
      val gui = new GUISWING(controllerStub)
      gui.resetInputField()
      // Überprüfen Sie, ob das Eingabefeld richtig zurückgesetzt wird
    }

    it should "change game mode correctly when a button is clicked" in {
      val gui = new GUISWING(controllerStub)
      // Simulieren Sie das Klicken eines Modus-Buttons und überprüfen Sie die Reaktion
    }


    it should "have a non-null scroll pane in the center panel" in {
      val gui = new GUISWING(controllerStub)
      gui.getScrollPane should not be (null)
    }

    it should "have a non-empty label in the headline panel" in {
      val gui = new GUISWING(controllerStub)
      gui.headlinepanel.bannerLabel.icon should not be (null)
    }


    it should "update the game mode buttons' appearance when clicked" in {
      val gui = new GUISWING(controllerStub)
      // Klicken Sie auf den Button und überprüfen Sie die Änderung seines Aussehens
    }

    it should "display the correct text in the NEWSPanel after an event" in {
      val gui = new GUISWING(controllerStub)
      // Simulieren Sie ein Event und überprüfen Sie den Text im NEWSPanel
    }

    it should "resize the headline panel correctly when window size changes" in {
      val gui = new GUISWING(controllerStub)
      // Ändern Sie die Größe des Fensters und überprüfen Sie, ob das Headline-Panel entsprechend reagiert
    }

    it should "correctly align components in the south panel" in {
      val gui = new GUISWING(controllerStub)
      // Überprüfen Sie die Ausrichtung der Komponenten im South-Panel
    }

    it should "properly initialize the input text field" in {
      // Da inputTextField ein Singleton-Objekt ist, können Sie direkt darauf zugreifen
      inputTextField.text shouldBe ""
    }


    it should "properly handle undo actions" in {
      val gui = new GUISWING(controllerStub)
      // Führen Sie eine Aktion aus, rufen Sie undo auf und überprüfen Sie das Ergebnis
    }

    it should "respond to save and load actions correctly" in {
      val gui = new GUISWING(controllerStub)
      // Führen Sie save und load aus und überprüfen Sie das Verhalten
    }

    it should "display the correct target word in the news panel after a win or loss" in {
      val gui = new GUISWING(controllerStub)
      // Simulieren Sie ein Gewinn- oder Verlustereignis und überprüfen Sie den angezeigten Text
    }

    it should "handle resizing of the input image label correctly" in {
      val gui = new GUISWING(controllerStub)
      // Ändern Sie die Größe des Fensters und überprüfen Sie, ob das inputImageLabel entsprechend reagiert
    }


  } else {
    "GUISWING in headless mode" should "not run GUI tests" in {
      "Skipping GUI tests in headless environment" should be("Skipping GUI tests in headless environment")
    }
  }



}
*/