package de.htwg.se.wordle.aview
import java.awt.{Color, Font, Graphics, Graphics2D, GraphicsEnvironment}
import java.io.{File, FileInputStream}
import javax.imageio.ImageIO
import scala.swing.*
import javax.swing.{ImageIcon, JPanel, JTextPane, SwingUtilities}


class JTextPaneWrapper(text: String) extends Component {
  override lazy val peer: JTextPane = new JTextPane {
    setContentType("text/html")
    setEditable(false)
    setBackground(new Color(0,0,0,0))
    setText(text)
    
  }

  
}

class BackgroundPanel(imagePath: String) extends JPanel {
  private val backgroundImage = ImageIO.read(new File(imagePath))

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    val panelWidth = this.getWidth
    val panelHeight = this.getHeight


    g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this)
  }

  def getPanelHeight: Int = this.getHeight
  def getBackgroundImage: Image = backgroundImage
}


class TexturedBackground(imagePath: String) extends BorderPanel {
  private val texture = ImageIO.read(new File(imagePath))
  peer.setOpaque(false)
  border = Swing.EmptyBorder(0, 0, 0, 0)

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)
    if (texture != null) {
      val parentComponent = SwingUtilities.getAncestorOfClass(classOf[BackgroundPanel], this.peer)
      parentComponent match {
        case backgroundPanel: BackgroundPanel =>
          val scaledHeight = backgroundPanel.getPanelHeight / 6
          val containerWidth = this.peer.getParent.getWidth
          val containerHeight = this.peer.getParent.getHeight
          val scaledImage = texture.getScaledInstance(containerWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH)
          // Berechnen der y-Position, um das Bild am unteren Rand zu positionieren
          val yPosition = containerHeight - scaledHeight
          g.drawImage(scaledImage, 0, yPosition, containerWidth, scaledHeight, this.peer)
        case _ =>

      }
      
    }
  }
  def gettextureImage: Image = texture
}


class TransparentButton(label: String) extends Button(label) {
  opaque = false
  contentAreaFilled = false
  borderPainted = false
  focusPainted = false // Fokus-Indikator nicht malen
  background = new Color(0, 0, 0, 0)
  foreground = Color.BLACK
  font = LoadCustomFont.loadFont("texturengui/font/Comicmeneu-Regular.ttf").deriveFont(26f) // Comic-Schriftart
  border = Swing.EmptyBorder(0, 0, 0, 0)
  peer.setOpaque(false)
}

//Um schriften aus Tuxturengui einlesen zu können
object LoadCustomFont {
  def loadFont(path: String): Font = {
    try {
      val fontStream = new FileInputStream(path)
      val font = Font.createFont(Font.TRUETYPE_FONT, fontStream)
      val ge = GraphicsEnvironment.getLocalGraphicsEnvironment
      ge.registerFont(font)
      font // Rückgabe des Font-Objekts
    } catch {
      case e: Exception =>
        e.printStackTrace()
        null
    }
  }
}

class ResizableBannerPanel(bannerPath: String) extends FlowPanel {
  val originalIcon = new ImageIcon(bannerPath)
  border = Swing.EmptyBorder(0, 0, 0, 0)

  val bannerLabel = new Label {
    icon = new ImageIcon(originalIcon.getImage.getScaledInstance(514, 157, java.awt.Image.SCALE_SMOOTH))
    border = Swing.EmptyBorder(0, 0, 10, 0)
  }

  contents += bannerLabel

  def updateBannerSize(frameWidth: Int): Unit = {
    val bannerWidth = frameWidth / 3
    val bannerHeight = (bannerWidth * 0.3).toInt

    val scaledImage = originalIcon.getImage.getScaledInstance(bannerWidth, bannerHeight, java.awt.Image.SCALE_SMOOTH)
    bannerLabel.icon = new ImageIcon(scaledImage)
  }
}

object FieldPanel extends Component {

  val wordleFontPaper: Font = LoadCustomFont.loadFont("texturengui/font/Wordlefont2-Regular.ttf").deriveFont(24f)

  var textPaneSeq: Seq[JTextPaneWrapper] = Seq.empty

  def createTextPane(part: String): JTextPaneWrapper = new JTextPaneWrapper(filterAndColor(part))

  def createTextfieldsFromSplit(Text: String): Seq[JTextPaneWrapper] = {
    val splitStrings = Text.split("\n\n")
    splitStrings.map(createTextPane)
  }

  def updateFieldPanel(input: String): Unit = {
    textPaneSeq = createTextfieldsFromSplit(input)

  }


  def GameFieldPanel(): BoxPanel = {
    new BoxPanel(Orientation.Horizontal) {
      contents ++= textPaneSeq
      background = new Color(0, 0, 0, 0)
    }
  }

  def filterAndColor(input: String): String = {
    val YellowPattern = """\u001B\[33m([^\u001B]+)\u001B\[0m""".r
    val GreenPattern = """\u001B\[32m([^\u001B]+)\u001B\[0m""".r

    val formattedInput = input
      .split("\n")
      .map { line =>
        val yellowColored = YellowPattern.replaceAllIn(line, m => s"<font color='#FFA500'>${m.group(1)}</font>") // Dunkleres Gelb
        val greenColored = GreenPattern.replaceAllIn(yellowColored, m => s"<font color='green'>${m.group(1)}</font>")
        s"<div style='text-align: center;'>$greenColored</div>" // Zentrierung des Textes
      }
      .mkString("")

    s"<html><body style='font-family:\"${wordleFontPaper.getName}\"; font-size:60pt;'>$formattedInput</body></html>"
  }
}

object NEWSPanel {
  val comicFont: Font = LoadCustomFont.loadFont("texturengui/font/Comicmeneu-Regular.ttf").deriveFont(24F)
  
  var newsBoardText = new Component {
    override lazy val peer: JTextPane = new JTextPane() {
      setContentType("text/html")
      setText(
        s"""
          <html>
            <head>
              <style>
                body {
                  font-family: \"${comicFont.getName}\";
                  font-size: 30pt; // Größe anpassen, wie benötigt
                  font-weight: bold;
                }
                .centered {
                  text-align: center;
                  display: table;
                  height: 100%;
                  width: 100%;
                }
                .centered div {
                  display: table-cell;
                  vertical-align: middle;
                }
              </style>
            </head>
            <body>
              <div class='centered'>
                <div>Wähle zwischen Leicht, Mittel und Schwer!</div>
              </div>
            </body>
          </html>
        """)
      setEditable(false)
      setOpaque(false)
      setBorder(null)
    }
  }

  def updateNewsBoardText(newText: String): Unit = {
    val htmlContent =
      s"""
      <html>
        <head>
          <style>
            body {
              font-family: \"${comicFont.getName}\";
              font-size: 30pt; // Größe anpassen, wie benötigt
              font-weight: bold;
            }
            .centered {
              text-align: center;
              display: table;
              height: 100%;
              width: 100%;
            }
            .centered div {
              display: table-cell;
              vertical-align: middle;
            }
          </style>
        </head>
        <body>
          <div class='centered'>
            <div>$newText</div>
          </div>
        </body>
      </html>
      """
    newsBoardText.peer.setText(htmlContent)
  }

  def NewsBoardPanel(): BoxPanel = {
    new BoxPanel(Orientation.Vertical) {
      opaque = false 
      
      contents += Swing.VStrut(80) // Abstand oben
      contents += newsBoardText
      contents += Swing.VStrut(35) // Abstand unten

      border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
    }
  }

}

object inputTextField extends TextField {
  val inputFont: Font = LoadCustomFont.loadFont("texturengui/font/Skia.ttf").deriveFont(24F)
  columns = 8 // Anzahl der Zeichen festlegen
  opaque = false
  enabled = false
  border = Swing.EmptyBorder(0, 0, 0, 0)
  background = new Color(0, 0, 0, 0) // Vollständig transparent
  peer.setCaretColor(Color.BLACK) // Farbe des Cursors
  peer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER) // Text zentrieren
  font = inputFont.deriveFont(24f)
}
