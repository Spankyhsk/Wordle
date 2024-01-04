package de.htwg.se.wordle.aview

import com.sun.glass.ui.Cursor.setVisible
import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{Command, EasyModeCommand, Event, HardModeCommand, MediumModeCommand, ModeSwitchInvoker, Observer}

import sun.tools.jconsole.LabeledComponent.layout

import java.awt.event.ComponentAdapter
import javax.swing.{JPanel, JScrollPane, JTextPane, SwingUtilities}
import scala.swing.*
import scala.swing.event.*
import java.awt.{BorderLayout, Color, FlowLayout, Graphics, GridBagConstraints, GridBagLayout}
import javax.swing.text.*
import javax.imageio.ImageIO
import java.io.File
import scala.swing.MenuBar.NoMenuBar.revalidate
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import java.awt.Image
import java.awt.{Graphics, Graphics2D, RenderingHints}
import scala.swing.Action.NoAction.text



class BackgroundPanel(imagePath: String) extends JPanel {
  private val backgroundImage = ImageIO.read(new File(imagePath))

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)

    // Ermitteln der Größe des Panels
    val panelWidth = this.getWidth
    val panelHeight = this.getHeight

    // Festlegen der neuen Größe des Bildes
    val newWidth = 1752
    val newHeight = 1236

    // Berechnen der Position, um das Bild in der Mitte des Panels zu zentrieren
    val x = (panelWidth - newWidth) / 2
    val y = (panelHeight - newHeight) / 2

    // Zeichnen des skalierten Bildes in der Mitte des Panels
    g.drawImage(backgroundImage, x, y, newWidth, newHeight, this)
  }
}




class TexturedBackground(imagePath: String) extends BorderPanel {
  private val texture = ImageIO.read(new File(imagePath))
  peer.setOpaque(false)
  border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze

  override def paintComponent(g: Graphics2D): Unit = {

    super.paintComponent(g)
    if (texture != null) {
      // Erhalten Sie die Breite und Höhe des übergeordneten Containers
      val containerBreite = this.peer.getParent.getWidth
      val containerHoehe = this.peer.getParent.getHeight

      // Setzen Sie die Höhe des Bildes so, dass es in das OutputPanel hineinragt
      val bildHoehe = (containerHoehe * 1.0).toInt // 40% der Containerhöhe, anpassen nach Bedarf

      // Skalieren Sie das Bild basierend auf der Containerbreite und der festgelegten Höhe
      val scaledImage = texture.getScaledInstance(containerBreite, bildHoehe, java.awt.Image.SCALE_SMOOTH)

      // Berechnen Sie die y-Position, um das Bild nach oben in das OutputPanel hineinragen zu lassen
      // Hier setzen wir es direkt am unteren Rand des Panels
      val yPosition = containerHoehe - bildHoehe

      // Zeichnen Sie das skalierte Bild an der berechneten y-Position
      g.drawImage(scaledImage, 0, yPosition, this.peer)
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
  font = new Font("Skia", Font.Plain.id, 18)
  border = Swing.EmptyBorder(0, 0, 0, 0)
  peer.setOpaque(false)
}



class GUISWING(controll:ControllerInterface) extends Frame with Observer {
  controll.add(this)
  var n = 1






  // Fenster-Titel und Skalierbarkeit
  title = "Wordle"
  resizable = true
  // Ändern Sie diese Werte, um die Startgröße des Fensters zu beeinflussen
  minimumSize = new Dimension(853, 682) // Erhöht von 300x600

  maximumSize = new Dimension(1280, 1024) // Erhöht von 1024x768

  // Schriftart definieren
  val customFont = new Font("Skia", Font.Plain.id, 14)





  // Stilfunktion für Komponenten
  def styleComponent(component: Component): Unit = {
    component.font = new Font("Skia", Font.Plain.id, 14)
    component.foreground = Color.BLACK
    component.background = Color.WHITE
  }


  menuBar = new MenuBar {
    contents += new Menu("Spiel beenden") {
      font = customFont.deriveFont(12)
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      }) {
        font = customFont.deriveFont(12)
      }
    }
  }


  //-------------------------------------
  val headlinepanel = new FlowPanel {
    // Pfad zu Ihrem Bannerbild
    val bannerPath = "/Users/steffen/Desktop/privat/Gekauft/FoldedPaperTextures/WordleBanner2.png"
    val originalIcon = new ImageIcon(bannerPath)
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze


    // Skalieren des Bildes
    val scaledImage = originalIcon.getImage.getScaledInstance(514, 157, java.awt.Image.SCALE_SMOOTH)
    val bannerIcon = new ImageIcon(scaledImage)

    contents += new Label {
      icon = bannerIcon
      border = Swing.EmptyBorder(0, 0, 10, 0)
    }
  }
  //------------------------------------
  val level = new Label("")


  val EasymodusButton = new TransparentButton("Leicht")
  val MediummodusButton = new TransparentButton("Mittel")
  val HardmodusButton = new TransparentButton("Schwer")


  val gamemoduspanel1 = new BoxPanel(Orientation.Horizontal){
    //contents += new Label("Schwierigkeitsgrad: ")
    contents += level
  }

  val gamemoduspanel2 = new BoxPanel(Orientation.Horizontal) {
    contents += EasymodusButton
    contents += Swing.HStrut(20) // Fügt einen horizontalen Abstand von 10 Pixeln hinzu
    contents += MediummodusButton
    contents += Swing.HStrut(20) // Fügt einen weiteren horizontalen Abstand von 10 Pixeln hinzu
    contents += HardmodusButton
  }


  val gamemoduspanelMain = new BoxPanel(Orientation.Vertical){
    contents += gamemoduspanel1
    contents += gamemoduspanel2

  }

  val northpanel = new BoxPanel(Orientation.Vertical){
    contents += menuBar
    contents += headlinepanel
    contents += gamemoduspanelMain
  }

  // Pfad zu Ihrem Eingabebild
  val inputImagePath = "/Users/steffen/Desktop/privat/Gekauft/FoldedPaperTextures/eingabepaper2.png"
  val originalIcon = new ImageIcon(inputImagePath)


  // Skalieren des Bildes
  val scaledImage = originalIcon.getImage.getScaledInstance(
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

  inputTextField.enabled = false
  // Erstellen Sie ein transparentes TextField
  object inputTextField extends TextField {
    columns = 8 // Anzahl der Zeichen festlegen
    opaque = false
    border = Swing.EmptyBorder(0, 0, 0, 0)
    background = new Color(0, 0, 0, 0) // Vollständig transparent
    peer.setCaretColor(Color.BLACK) // Farbe des Cursors
    peer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER) // Text zentrieren
    font = new Font("Skia", Font.Plain.id, 24) // Schriftgröße
  }

  // JTextPane für Output
  object OutputTextField extends Component {
    override lazy val peer: JTextPane = new JTextPane() {
      setContentType("text/html")
      setEditable(false)
      setBackground(new Color(0, 0, 0, 0)) // Hintergrund transparent machen
    }
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



  //--------------------------------------------------

  // Das OutputPanel, das das OutputTextField enthält
  // OutputPanel, das OutputTextField enthält
  val OutputPanel = new BoxPanel(Orientation.Vertical) {
    contents += Component.wrap(OutputTextField.peer)
    font = customFont.deriveFont(18)
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
  }

  val centerPanelSize = new Dimension(300, 300) // Festgelegte Größe für das centerPanel

  val centerPanel = new Panel {
    preferredSize = centerPanelSize
    minimumSize = centerPanelSize
    maximumSize = centerPanelSize

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
    val scrollPane = new ScrollPane(OutputPanel) {
      border = Swing.EmptyBorder(0, 0, 0, 0)
      override lazy val peer: JScrollPane = new JScrollPane(OutputPanel.peer) with SuperMixin {
        setOpaque(false)
        getViewport.setOpaque(false)
      }
    }
    peer.add(scrollPane.peer, c)
  }


  //--------------------
  // Erstellen Sie die TextArea für das NewsBoard
  val newsBoardText = new Component {
    override lazy val peer: JTextPane = new JTextPane() {
      setContentType("text/html")
      setText(
        s"""
        <html>
          <head>
            <style>
              body {
                font-family: 'Skia';
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
            font-family: 'Skia';
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


  val newsBoardPanel = new BoxPanel(Orientation.Vertical) {
    opaque = false // Stellen Sie sicher, dass das Panel transparent ist

    // Fügen Sie vertikale Struts hinzu, um den Text vertikal zu zentrieren
    contents += Swing.VStrut(80) // Abstand oben
    contents += newsBoardText
    contents += Swing.VStrut(60) // Abstand unten

    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
  }


  val texturedBackground = new TexturedBackground("/Users/steffen/Desktop/privat/Gekauft/FoldedPaperTextures/4rippedpaperneu.png") {
    layout(newsBoardPanel) = BorderPanel.Position.Center
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
  }

  val southPanel = new BorderPanel {
    border = Swing.LineBorder(java.awt.Color.BLACK)
    add(texturedBackground, BorderPanel.Position.Center)
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
  }


  //-------------------------------------
  contents = new BorderPanel {

    add(northpanel, BorderPanel.Position.North)
    add(centerPanel, BorderPanel.Position.Center)
    add(southPanel, BorderPanel.Position.South)
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze

  }


  val modeSwitchInvoker = new ModeSwitchInvoker()

  listenTo(inputTextField, EasymodusButton, MediummodusButton, HardmodusButton)
  var won = false
  reactions +={
    case EditDone(inputTextField) =>
      val guess = controll.GuessTransform(inputTextField.text)

      if(controll.controllLength(guess.length)){
        controll.set(n, controll.evaluateGuess(guess))
        if(controll.areYouWinningSon(guess)){
          updateNewsBoardText("Glückwunsch!! Du hast Gewonnen.\n zum erneuten Spielen Schwierigkeitsgrad aussuchen")
          inputTextField.enabled = false

          //controller.set(n, controller.evaluateGuess(guess))
          won = true
        }
      }else{
        updateNewsBoardText("Falsche Eingabe")
        n = n -1

      }
      if (!controll.count(n) && !won) {
        updateNewsBoardText("Verloren!\n zum erneuten Spielen Schwierigkeitsgrad aussuchen")
        inputTextField.enabled = false

      } else {
        n = n + 1

      }

      resetInputField()


    case ButtonClicked(EasymodusButton)=>
      //modeSwitchInvoker.setCommand(EasyModeCommand(Some(controll)))
      //modeSwitchInvoker.executeCommand()

      startNewGame(1)
      controll.changeState(1)
      controll.createGameboard()
      controll.createwinningboard()
      updateButtonColors(EasymodusButton)
      inputTextField.enabled = true
      level.text = ""
      updateNewsBoardText("Errate 1 Wort aus 5 Buchstaben, du hast 3 Versuche")
      inputTextField.enabled = true
      n = 1
    case ButtonClicked(MediummodusButton)=>
      //modeSwitchInvoker.setCommand(MediumModeCommand(Some(controll)))
      //modeSwitchInvoker.executeCommand()

      startNewGame(2)
      controll.changeState(2)
      controll.createGameboard()
      controll.createwinningboard()
      updateButtonColors(MediummodusButton)
      inputTextField.enabled = true
      level.text = ""
      updateNewsBoardText("Errate 2 Wörter mit je 5 Buchstaben, du hast 4 Versuche")
      inputTextField.enabled = true
      n = 1
    case ButtonClicked(HardmodusButton)=>
      //modeSwitchInvoker.setCommand(HardModeCommand(Some(controll)))
      //modeSwitchInvoker.executeCommand()

      startNewGame(3)
      controll.changeState(3)
      controll.createGameboard()
      controll.createwinningboard()
      updateButtonColors(HardmodusButton)
      inputTextField.enabled = true
      level.text = ""
      updateNewsBoardText("Errate 4 Wörter mit je 5 Buchstaben, du hast 5 Versuche")
      inputTextField.enabled = true
      n = 1

  }

  // Laden des Hintergrundbilds
  val backgroundPanel = new BackgroundPanel("/Users/steffen/Desktop/privat/Gekauft/FoldedPaperTextures/3background.jpg")
  northpanel.peer.setOpaque(false)
  centerPanel.peer.setOpaque(false)
  southPanel.peer.setOpaque(false)
  inputTextField.peer.setOpaque(false)
  OutputTextField.peer.setOpaque(false)
  gamemoduspanelMain.peer.setOpaque(false)
  headlinepanel.peer.setOpaque(false)
  gamemoduspanel1.peer.setOpaque(false)
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

  pack()


  def resetInputField(): Unit = {
    javax.swing.SwingUtilities.invokeLater(new Runnable {
      def run(): Unit = {
        inputTextField.text = ""
      }
    })
  }

  def startNewGame(difficulty: Int): Unit = {
    controll.changeState(difficulty)
    controll.createGameboard()
    controll.createwinningboard()
    level.text = difficultyLevelToString(difficulty)
    updateNewsBoardText("Spiel gestartet im Modus: ${level.text}")
    OutputTextField.peer.setText("") // Text zurücksetzen
    OutputTextField.peer.repaint() // Komponente neu zeichnen
    won = false
    n = 1
  }


  def difficultyLevelToString(difficulty: Int): String = difficulty match {
    case 1 => "leicht"
    case 2 => "mittel"
    case 3 => "schwer"
    case _ => "unbekannt"
  }

  override def update(e:Event):Unit={
    e match
      case Event.Move =>{
        if (!won) { // Wenn das Spiel noch nicht gewonnen wurde
          val currentGameState = controll.toString
          val filteredAndColoredText = filterAndColor(currentGameState)
          OutputTextField.peer.setText(filteredAndColoredText)
          OutputTextField.peer.setCaretPosition(0)
        }
      }
      /*case Event.NEW =>{
        newsBoard.text
        level.text
        //controll.createGameboard()
        //controll.createwinningboard()
        inputTextField.enabled = true
        //n = 1

      }*/

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

    s"<html><body style='font-family:Skia; font-size:60pt;'>$formattedInput</body></html>"
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




  pack()
  centerOnScreen()
  open()
  peer.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH) // Fenster maximieren


}
