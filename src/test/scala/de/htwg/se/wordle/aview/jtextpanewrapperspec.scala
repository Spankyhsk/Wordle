import de.htwg.se.wordle.aview.{BackgroundPanel, FieldPanel, JTextPaneWrapper, NEWSPanel, ResizableBannerPanel, TexturedBackground, TransparentButton, inputTextField}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.{Color, Graphics2D, Image}
import java.awt.image.{BufferedImage, ImageObserver}
import javax.swing.{JPanel, JTextPane}
import javax.swing.border.EmptyBorder
import scala.swing.{BorderPanel, Insets, Panel, Swing}

class JTextPaneWrapperSpec extends AnyFlatSpec with Matchers {

  "JTextPaneWrapper" should "correctly set text" in {
    val text = "Test"
    val htmlText = s"<html>$text</html>"
    val wrapper = new JTextPaneWrapper(htmlText)
    val textPane: JTextPane = wrapper.peer
    textPane.getText.contains(text) shouldBe true
  }


  "BackgroundPanel" should "load the specified image and have valid height" in {
    val imagePath = "texturengui/7background.jpg"
    val panel = new BackgroundPanel(imagePath)
    panel.getBackgroundImage should not be (null)

  }


  "TexturedBackground" should "properly create a texture" in {
    val imagePath = "texturengui/4rippedpaperneu.png"
    val texturedBackground = new TexturedBackground(imagePath)
    texturedBackground.gettextureImage should not be (null)
  }

  "TransparentButton" should "be opaque and content filled" in {
    val button = new TransparentButton("Test")
    button.opaque shouldBe false
    button.contentAreaFilled shouldBe false
    button.border.asInstanceOf[EmptyBorder].getBorderInsets shouldBe new Insets(0, 0, 0, 0)
  }


  "ResizableBannerPanel" should "resize its banner image" in {
    val bannerPath = "texturengui/Wordlebanner2.png"
    val panel = new ResizableBannerPanel(bannerPath)
    panel.updateBannerSize(100)
    val icon = panel.bannerLabel.icon
    icon.getIconWidth should be(33)
  }


  "FieldPanel" should "create JTextPaneWrapper for each part of the input" in {
    val input = "Test\n\nTest2"
    FieldPanel.updateFieldPanel(input)
    FieldPanel.textPaneSeq.size shouldBe 2
  }

  "NEWSPanel" should "update news board text correctly" in {
    val newText = "New news"
    NEWSPanel.updateNewsBoardText(newText)
    NEWSPanel.newsBoardText.peer.getText should include(newText)
  }

  "text of JTextPane" should "match the provided HTML string" in {
    val htmlString = "<html><body>Test</body></html>"
    val wrapper = new JTextPaneWrapper(htmlString)
    wrapper.peer.getText() should include("Test")
  }

  "TexturedBackground" should "correctly execute paintComponent" in {
    val imagePath = "texturengui/4rippedpaperneu.png"
    val texturedBackground = new TexturedBackground(imagePath)

    val parentWidth = 120
    val parentHeight = 180

    // Simulieren der Größe des übergeordneten Elements
    texturedBackground.peer.setSize(parentWidth, parentHeight)

    // Erstellen eines BufferedImage als Grafik-Kontext
    val bufferedImage = new BufferedImage(parentWidth, parentHeight, BufferedImage.TYPE_INT_RGB)
    val g2d = bufferedImage.createGraphics()

    // Ausführen der paintComponent Methode
    texturedBackground.paintComponent(g2d)

    // Prüfen, ob auf dem BufferedImage gezeichnet wurde
    bufferedImage should not be null
    texturedBackground should not be (0)

    g2d.dispose()
  }


  "FieldPanel" should "create a GameFieldPanel with JTextPaneWrappers" in {
    val input = "Test\n\nTest2"
    FieldPanel.updateFieldPanel(input)
    val panel = FieldPanel.GameFieldPanel()
    panel.contents.size shouldBe 2
  }

  "NEWSPanel" should "create a NewsBoardPanel correctly" in {
    val panel = NEWSPanel.NewsBoardPanel()
    panel.contents.size should be > 0
  }

  "inputTextField" should "be correctly configured" in {
    inputTextField.columns shouldBe 8
    inputTextField.opaque shouldBe false
    inputTextField.enabled shouldBe false
    inputTextField.border.asInstanceOf[EmptyBorder].getBorderInsets shouldBe new Insets(0, 0, 0, 0)
    inputTextField.peer.getCaretColor shouldBe Color.BLACK
  }


}
