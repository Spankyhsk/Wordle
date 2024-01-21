import de.htwg.se.wordle.aview.{BackgroundPanel, FieldPanel, JTextPaneWrapper, NEWSPanel, ResizableBannerPanel, TexturedBackground, TransparentButton}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import javax.swing.JTextPane
import javax.swing.border.EmptyBorder
import scala.swing.{Insets, Swing}

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
    //panel.getPanelHeight should be > 0
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

}
