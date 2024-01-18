package de.htwg.se.wordle.aview

import com.sun.glass.ui.Cursor.setVisible
import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{Command, EasyModeCommand, Event, HardModeCommand, MediumModeCommand, Observer}

import de.htwg.se.wordle.aview.JTextPaneWrapper
import sun.tools.jconsole.LabeledComponent.layout
import java.awt.event.{ComponentAdapter, ComponentEvent}
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.FileInputStream
import java.awt.event.ComponentAdapter
import javax.swing.{JPanel, JScrollPane, JTextPane, SwingUtilities}
import scala.swing.*
import scala.swing.event.*
import java.awt.{BorderLayout, Color, FlowLayout, Graphics, Graphics2D, GraphicsEnvironment, GridBagConstraints, GridBagLayout, Image, RenderingHints}
import javax.swing.text.*
import javax.imageio.ImageIO
import java.io.{File, FileInputStream}
import scala.swing.MenuBar.NoMenuBar.revalidate
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import scala.swing.Action.NoAction.text



class BackgroundPanel(imagePath: String) extends JPanel {
  private val backgroundImage = ImageIO.read(new File(imagePath))

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    val panelWidth = this.getWidth
    val panelHeight = this.getHeight


    g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this)
  }

  def getPanelHeight: Int = this.getHeight
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



class GUISWING(controll:ControllerInterface) extends Frame with Observer {
  controll.add(this)
  //controll.setVersuche(1)

  // Fenster-Titel und Skalierbarkeit
  title = "Wordle"
  resizable = true
  // Ändern Sie diese Werte, um die Startgröße des Fensters zu beeinflussen
  //minimumSize = new Dimension(640, 512) // Erhöht von 300x600

  //maximumSize = new Dimension(1280, 1024) // Erhöht von 1024x768

  // Schriftart definieren
  val customFont = new Font("Skia", Font.PLAIN, 14)

  val wordleFontPaper: Font = LoadCustomFont.loadFont("texturengui/font/Wordlefont2-Regular.ttf").deriveFont(24f)
  val comicFont: Font = LoadCustomFont.loadFont("texturengui/font/Comicmeneu-Regular.ttf").deriveFont(24F)
  val inputFont: Font = LoadCustomFont.loadFont("texturengui/font/Skia.ttf").deriveFont(24F)




  menuBar = new MenuBar {
    contents += new Menu("Menü") {
      font = comicFont.deriveFont(12)
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      }) {
        font = comicFont.deriveFont(12)
      }
      contents += new MenuItem(Action("Save") {
        controll.save()
      }) {
        font = comicFont.deriveFont(12)
      }
      contents += new MenuItem(Action("Load") {
        controll.load()
      }) {
        font = comicFont.deriveFont(12)
      }
    }

  }

  peer.addComponentListener(new ComponentAdapter {
    override def componentResized(e: ComponentEvent): Unit = {
      headlinepanel.updateBannerSize(peer.getSize().width)
    }
  })


  //-------------------------------------
  val headlinepanel = new ResizableBannerPanel("texturengui/Wordlebanner2.png")

  peer.addComponentListener(new ComponentAdapter {
    override def componentResized(e: ComponentEvent): Unit = {
      headlinepanel.updateBannerSize(peer.getSize().width)
    }
  })
  //------------------------------------


  val EasymodusButton = new TransparentButton("Leicht")
  val MediummodusButton = new TransparentButton("Mittel")
  val HardmodusButton = new TransparentButton("Schwer")


  val gamemoduspanel2 = new BoxPanel(Orientation.Horizontal) {
    contents += EasymodusButton
    contents += Swing.HStrut(20) // Fügt einen horizontalen Abstand von 10 Pixeln hinzu
    contents += MediummodusButton
    contents += Swing.HStrut(20) // Fügt einen weiteren horizontalen Abstand von 10 Pixeln hinzu
    contents += HardmodusButton
  }


  val gamemoduspanelMain = new BoxPanel(Orientation.Vertical){//Methode
    contents += gamemoduspanel2

  }

  var northpanel = new BoxPanel(Orientation.Vertical) {
    contents += menuBar
    contents += headlinepanel
    contents += gamemoduspanelMain//muss updatebar sein
  }

  def upgradegamemoduspanel(clickedButton: Button):Unit={
    updateButtonColors(clickedButton: Button)
    gamemoduspanelMain.contents.clear()
    gamemoduspanelMain.contents += gamemoduspanel2
    gamemoduspanelMain.revalidate()
    gamemoduspanelMain.repaint()
  }

  def updateButtonColors(clickedButton: Button): Unit = {
    val greenColor = new Color(0, 128, 0) // HTML-Grün

    val buttons = List(EasymodusButton, MediummodusButton, HardmodusButton)
    buttons.foreach { button =>
      if (button == clickedButton) {
        button.foreground = greenColor
      } else {
        button.foreground = Color.BLACK
      }
    }
  }
  // Pfad zu Ihrem Eingabebild
  val inputImagePath = "texturengui/eingabepaper2.png"
  val originalIcon = new ImageIcon(inputImagePath)


  // Skalieren des Bildes
  var scaledImage = originalIcon.getImage.getScaledInstance(
    170, // Breite anpassen
    70, // Höhe anpassen
    java.awt.Image.SCALE_SMOOTH
  )
  val inputImageIcon = new ImageIcon(scaledImage)

  // Erstellen Sie ein JLabel mit dem skalierten Bild als Hintergrund
  val inputImageLabel = new Label {
    icon = inputImageIcon
    xLayoutAlignment = 0.5
    yLayoutAlignment = 0.5
  }

  
  // Erstellen Sie ein transparentes TextField
  object inputTextField extends TextField {
    columns = 8 // Anzahl der Zeichen festlegen
    opaque = false
    enabled = false
    border = Swing.EmptyBorder(0, 0, 0, 0)
    background = new Color(0, 0, 0, 0) // Vollständig transparent
    peer.setCaretColor(Color.BLACK) // Farbe des Cursors
    peer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER) // Text zentrieren
    font = inputFont.deriveFont(24f)
  }

  // Fügen Sie das Eingabebild und das TextField in einem Panel zusammen
  val InputPanel = new Panel {
    peer.setLayout(new GridBagLayout())
    val c = new GridBagConstraints()

    // Einstellungen für das Textfeld
    c.gridx = 0
    c.gridy = 0
    c.weightx = 0
    c.weighty = 0
    c.anchor = GridBagConstraints.CENTER
    c.insets = new Insets(25, 10, 0, 0) // Feinabstimmung der Position
    c.ipadx = -20 // Interne Polsterung
    c.ipady = 20 // Höhe des Textfelds
    peer.add(inputTextField.peer, c) // Korrekte Verwendung von inputTextField

    // Einstellungen für das Bild
    c.gridy = 1
    c.insets = new Insets(-68, 10, 0, 0)
    peer.add(inputImageLabel.peer, c)

    border = Swing.EmptyBorder(0, 0, 0, 0)
    opaque = false
  }

  inputTextField.opaque = false
  inputTextField.border = Swing.EmptyBorder(0, 0, 0, 0)
  inputTextField.background = new Color(0, 0, 0, 0) // Vollständig transparent
  inputTextField.peer.setCaretColor(Color.BLACK) // Farbe des Cursors


  val OutputPanel = new BoxPanel(Orientation.Vertical) {
    contents += FieldPanel.GameFieldPanel()
  }//Klappt

  val centerPanelSize = new Dimension(300, 300) // Festgelegte Größe für das centerPanel

  val scrollPane = new ScrollPane(OutputPanel) {
    border = Swing.EmptyBorder(0, 0, 0, 0)
    override lazy val peer: JScrollPane = new JScrollPane(OutputPanel.peer) with SuperMixin {
      setOpaque(false)
      getViewport.setOpaque(false)
    }
  }

  val centerPanel = new BoxPanel(Orientation.Vertical) {
    //preferredSize = centerPanelSize
    //minimumSize = centerPanelSize
    //maximumSize = centerPanelSize

    peer.setLayout(new GridBagLayout())



    // Konfiguration für das Eingabefeld
    val c = new GridBagConstraints()
    c.gridx = 0
    c.gridy = 0
    c.gridwidth = 1
    c.anchor = GridBagConstraints.NORTH
    c.weightx = 0.5
    c.weighty = 0.0
    peer.add(InputPanel.peer, c)

    // Konfiguration für das OutputPanel
    c.gridy = 1
    c.weighty = 1.0 // Das restliche Gewicht wird dem OutputPanel zugewiesen
    c.fill = GridBagConstraints.BOTH


    peer.add(scrollPane.peer, c)
  }


  val texturedBackground = new TexturedBackground("texturengui/4rippedpaperneu.png") {
    layout(NEWSPanel.NewsBoardPanel()) = BorderPanel.Position.Center
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
  }

  val southPanel = new BoxPanel(Orientation.Vertical) {
    border = Swing.LineBorder(java.awt.Color.BLACK)
    contents += texturedBackground
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
  }


  //-------------------------------------
  

  listenTo(inputTextField, EasymodusButton, MediummodusButton, HardmodusButton)
  //var won = false
  reactions +={
    case EditDone(inputTextField) =>
      val guess = controll.GuessTransform(inputTextField.text)

      if(controll.controllLength(guess.length)&& controll.controllRealWord(guess)){
        if(!controll.areYouWinningSon(guess) && controll.count()){
          controll.set(controll.getVersuche(), controll.evaluateGuess(guess))
          controll.setVersuche(controll.getVersuche() + 1)
        }else{
          controll.set(controll.getVersuche(), controll.evaluateGuess(guess))
        }
      }else{
        NEWSPanel.updateNewsBoardText("Falsche Eingabe")
      }

      resetInputField()


      
    case ButtonClicked(EasymodusButton)=>

      upgradegamemoduspanel(EasymodusButton)
      NEWSPanel.updateNewsBoardText("Errate 1 Wort aus 5 Buchstaben, du hast 3 Versuche")
      controll.changeState(1)
      controll.createGameboard()
      controll.createwinningboard()

    case ButtonClicked(MediummodusButton)=>

      NEWSPanel.updateNewsBoardText("Errate 2 Wörter mit je 5 Buchstaben, du hast 4 Versuche")
      upgradegamemoduspanel(MediummodusButton)
      controll.changeState(2)
      controll.createGameboard()
      controll.createwinningboard()

    case ButtonClicked(HardmodusButton)=>

      NEWSPanel.updateNewsBoardText("Errate 4 Wörter mit je 5 Buchstaben, du hast 5 Versuche")
      upgradegamemoduspanel(HardmodusButton)
      controll.changeState(3)
      controll.createGameboard()
      controll.createwinningboard()
      
  }

  // Laden des Hintergrundbilds
  val backgroundPanel = new BackgroundPanel("texturengui/7background.jpg")
  northpanel.peer.setOpaque(false)
  centerPanel.peer.setOpaque(false)
  southPanel.peer.setOpaque(false)
  inputTextField.peer.setOpaque(false)
  //OutputTextField.peer.setOpaque(false)
  gamemoduspanelMain.peer.setOpaque(false)
  headlinepanel.peer.setOpaque(false)
  //gamemoduspanel1.peer.setOpaque(false)
  gamemoduspanel2.peer.setOpaque(false)
  InputPanel.peer.setOpaque(false)
  OutputPanel.peer.setOpaque(false)
  //newsBoardPanel.peer.setOpaque(false)  // Verwenden Sie newsBoardPanel anstelle von newsBoard



  // Erstellen Sie ein Scala Swing Panel, das das BackgroundPanel beinhaltet
  val scalaBackgroundPanel = new Panel {
    override lazy val peer: JPanel = backgroundPanel
  }

  // Fügen Sie Ihre Panels zum BackgroundPanel hinzu
  backgroundPanel.setLayout(new BorderLayout())
  backgroundPanel.add(northpanel.peer, BorderLayout.NORTH)
  backgroundPanel.add(centerPanel.peer, BorderLayout.CENTER)
  backgroundPanel.add(southPanel.peer, BorderLayout.SOUTH)

  // Fügen Sie das scalaBackgroundPanel zum Frame hinzu
  this.contents = scalaBackgroundPanel



  def resetInputField(): Unit = {
    javax.swing.SwingUtilities.invokeLater(new Runnable {
      def run(): Unit = {
        inputTextField.text = ""
      }
    })
  }

  override def update(e:Event):Unit={
    e match
      case Event.Move =>{

        SwingUtilities.invokeLater(new Runnable {
          def run(): Unit = {
            // Speichern der aktuellen Scroll-Position
            val scrollPos = scrollPane.verticalScrollBar.value

            // Aktualisieren des OutputPanel
            val currentGameState = controll.toString
            FieldPanel.updateFieldPanel(currentGameState)
            OutputPanel.contents.clear()
            OutputPanel.contents += FieldPanel.GameFieldPanel()
            OutputPanel.revalidate()
            OutputPanel.repaint()

            // Wiederherstellen der Scroll-Position nach dem Repaint/Revalidate
            SwingUtilities.invokeLater(new Runnable {
              def run(): Unit = {
                scrollPane.verticalScrollBar.value = scrollPos
              }
            })
          }
        })

      }
      case Event.NEW =>{//Hat ein Deadlock oder so bzw wenn man die changestate aufruft
        controll.setVersuche(1)
        inputTextField.enabled = true
      }
      case Event.WIN =>{
        NEWSPanel.updateNewsBoardText("Glückwunsch!! Du hast Gewonnen.\n zum erneuten Spielen Schwierigkeitsgrad aussuchen")
        inputTextField.enabled = false
      }
      case Event.LOSE =>{
        NEWSPanel.updateNewsBoardText("Verloren!\n zum erneuten Spielen Schwierigkeitsgrad aussuchen")
        inputTextField.enabled = false

      }
      case Event.UNDO => {
        SwingUtilities.invokeLater(new Runnable {
          def run(): Unit = {
            // Speichern der aktuellen Scroll-Position
            val scrollPos = scrollPane.verticalScrollBar.value

            // Aktualisieren des OutputPanel
            val currentGameState = controll.toString
            FieldPanel.updateFieldPanel(currentGameState)
            OutputPanel.contents.clear()
            OutputPanel.contents += FieldPanel.GameFieldPanel()
            OutputPanel.revalidate()
            OutputPanel.repaint()

            // Wiederherstellen der Scroll-Position nach dem Repaint/Revalidate
            SwingUtilities.invokeLater(new Runnable {
              def run(): Unit = {
                scrollPane.verticalScrollBar.value = scrollPos
              }
            })
          }
        })
      }


  }


  object FieldPanel extends Component {

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

  object NEWSPanel{
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

      def NewsBoardPanel():BoxPanel={
        new BoxPanel(Orientation.Vertical) {
          opaque = false // Stellen Sie sicher, dass das Panel transparent ist

          // Fügen Sie vertikale Struts hinzu, um den Text vertikal zu zentrieren
          contents += Swing.VStrut(80) // Abstand oben
          contents += newsBoardText
          contents += Swing.VStrut(35) // Abstand unten

          border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
        }
      }

      def updateNewsBoardPanel(): Unit = {

      }


  }

  pack()
  centerOnScreen()
  open()
  peer.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH)// Fenster maximieren


}
