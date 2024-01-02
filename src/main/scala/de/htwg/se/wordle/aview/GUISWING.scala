package de.htwg.se.wordle.aview

import com.sun.glass.ui.Cursor.setVisible
import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{Command, EasyModeCommand, Event, HardModeCommand, MediumModeCommand, ModeSwitchInvoker, Observer}
import sun.tools.jconsole.LabeledComponent.layout
import javax.swing.SwingUtilities
import scala.swing.*
import scala.swing.event.*
import java.awt.{BorderLayout, Color, FlowLayout, Graphics, GridBagConstraints, GridBagLayout}
import javax.swing.{JPanel, JTextPane}
import javax.swing.text.*
import javax.imageio.ImageIO
import java.io.File
import scala.swing.MenuBar.NoMenuBar.revalidate
import javax.imageio.ImageIO




class BackgroundPanel(imagePath: String) extends JPanel {
  private val backgroundImage = ImageIO.read(new File(imagePath))

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    g.drawImage(backgroundImage, 0, 0, this.getWidth, this.getHeight, this)
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
}



class GUISWING(controll:ControllerInterface) extends Frame with Observer {
  controll.add(this)
  var n = 1






  // Fenster-Titel und Skalierbarkeit
  title = "Wordle"
  resizable = true
  // Ändern Sie diese Werte, um die Startgröße des Fensters zu beeinflussen
  minimumSize = new Dimension(300, 600) // Erhöht von 300x600
  preferredSize = new Dimension(700, 800) // Neue bevorzugte Größe
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
  val headlinepanel = new FlowPanel{
    contents += new Label("Wordle"){
      font = customFont.deriveFont(60f)
      border = Swing.EmptyBorder(0, 0, 10, 0)
    }
  }
  //------------------------------------
  val level = new Label("Wähle deinen Schwierigkeitsgrad")


  val EasymodusButton = new TransparentButton("Leicht")
  val MediummodusButton = new TransparentButton("Mittel")
  val HardmodusButton = new TransparentButton("Schwer")


  val gamemoduspanel1 = new BoxPanel(Orientation.Horizontal){
    //contents += new Label("Schwierigkeitsgrad: ")
    contents += level
  }

  val gamemoduspanel2 = new BoxPanel(Orientation.Horizontal){
    contents += EasymodusButton
    contents += MediummodusButton
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





  //------------------------------------------------------
  object inputTextField extends TextField {
    columns = 10
    preferredSize = new Dimension(200, 30)
    maximumSize = new Dimension(200, 30)
    enabled = false
  }

  // JTextPane für Output
  object OutputTextField extends Component {
    override lazy val peer: JTextPane = new JTextPane() {
      setContentType("text/html")
      setEditable(false)

    }
  }


  val InputPanel = new Panel {
    preferredSize = new Dimension(300, 50) // Beispielgröße
    maximumSize = preferredSize
    minimumSize = preferredSize
    font = customFont.deriveFont(18)

    peer.setLayout(new GridBagLayout())
    val c = new GridBagConstraints()

    c.gridx = GridBagConstraints.REMAINDER
    c.gridy = 0
    c.anchor = GridBagConstraints.CENTER

    // Hinzufügen der Beschriftung "Versuch:"
    val label = new Label("Versuch:")
    peer.add(label.peer, c)


    c.gridy = 1
    peer.add(inputTextField.peer, c)

  }



  //--------------------------------------------------

  // Das OutputPanel, das das OutputTextField enthält
  // OutputPanel, das OutputTextField enthält
  val OutputPanel = new BoxPanel(Orientation.Vertical) {
    contents += Component.wrap(OutputTextField.peer)
    font = customFont.deriveFont(18)
  }

  val centerPanelSize = new Dimension(300, 300) // Festgelegte Größe für das centerPanel

  val centerPanel = new Panel {
    preferredSize = centerPanelSize
    minimumSize = centerPanelSize
    maximumSize = centerPanelSize

    peer.setLayout(new GridBagLayout())
    val c = new GridBagConstraints()

    // Konfiguration für das zentrierte InputPanel
    c.gridx = 0
    c.gridy = 0
    c.gridwidth = 1
    c.anchor = GridBagConstraints.CENTER
    c.weightx = 0.5
    c.weighty = 0.5
    peer.add(InputPanel.peer, c)

    // Konfiguration für das OutputPanel innerhalb eines ScrollPane
    c.gridy = 1
    c.weighty = 1.0
    c.fill = GridBagConstraints.BOTH
    val scrollPanel = new ScrollPane(Component.wrap(OutputPanel.peer)) {
      preferredSize = new Dimension(280, 250) // Größe des ScrollPane anpassen
      maximumSize = preferredSize
      minimumSize = preferredSize
    }
    peer.add(scrollPanel.peer, c)
  }


  //--------------------
  object newsBoard extends TextArea {
    columns = 20
    rows = 3
    text = "Wähle deinen Schwierigkeitsgrad" // Text geändert
    editable = false
    lineWrap = true
    wordWrap = true
    font = customFont.deriveFont(22)
    peer.setLayout(new FlowLayout(FlowLayout.CENTER)) // Setzt das Layout auf FlowLayout mit zentrierter Ausrichtung
  }
  val southPanel = new BorderPanel {
    border = Swing.LineBorder(java.awt.Color.BLACK)
    add(newsBoard, BorderPanel.Position.Center)
    layout(newsBoard) = BorderPanel.Position.Center // Zentriert das newsBoard im southPanel
    font = customFont.deriveFont(18)
  }


  //-------------------------------------
  contents = new BorderPanel {

    add(northpanel, BorderPanel.Position.North)
    add(centerPanel, BorderPanel.Position.Center)
    add(southPanel, BorderPanel.Position.South)

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
          newsBoard.text = "Glückwunsch!! Du hast Gewonnen.\n zum erneuten Spielen Schwierigkeitsgrad aussuchen"
          inputTextField.enabled = false

          //controller.set(n, controller.evaluateGuess(guess))
          won = true
        }
      }else{
        newsBoard.text = "Falsche Eingabe"
        n = n -1

      }
      if (!controll.count(n) && !won) {
        newsBoard.text = "Verloren!\n zum erneuten Spielen Schwierigkeitsgrad aussuchen"
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
      newsBoard.text = "Errate 1 Wort mit 1 guess bevor die Versuche ausgehen"
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
      newsBoard.text = "Errate 2 Wörter mit 1 guess bevor die Versuche ausgehen"
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
      newsBoard.text = "Errate 4 Wörter mit 1 guess bevor die Versuche ausgehen"
      inputTextField.enabled = true
      n = 1

  }

  // Laden des Hintergrundbilds
  val backgroundPanel = new BackgroundPanel("/Users/steffen/Desktop/privat/Gekauft/FoldedPaperTextures/3.jpg")
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
  newsBoard.peer.setOpaque(false)
  

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
    newsBoard.text = s"Spiel gestartet im Modus: ${level.text}"
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



}
