package de.htwg.se.wordle.aview

import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{Command, EasyModeCommand, Event, HardModeCommand, MediumModeCommand, ModeSwitchInvoker, Observer}
import sun.tools.jconsole.LabeledComponent.layout

import scala.swing.*
import scala.swing.event.*
import java.awt.{Color, GridBagConstraints, GridBagLayout}
import javax.swing.JTextPane
import javax.swing.text.*

class GUISWING(controll:ControllerInterface) extends Frame with Observer {
  controll.add(this)
  var n = 1
  // Fenster-Titel und Skalierbarkeit
  title = "Wordle"
  resizable = true
  minimumSize = new Dimension(300, 600)
  maximumSize = new Dimension(1024, 768)

  // Stilfunktion für Komponenten
  def styleComponent(component: Component): Unit = {
    component.font = new Font("Arial", Font.Plain.id, 14)
    component.foreground = Color.BLACK
    component.background = Color.WHITE
  }


  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      })
    }
  }




  //-------------------------------------
  val headlinepanel = new FlowPanel{
    contents += new Label("Wordle"){
      font = new Font("Arial", Font.Bold.id, 24)
      border = Swing.EmptyBorder(0, 0, 10, 0)
    }
  }
  //------------------------------------
  val level = new Label("???")


  val EasymodusButton = new Button("Leicht")
  val MediummodusButton = new Button("Mittel")
  val HardmodusButton = new Button("Schwer")


  val gamemoduspanel1 = new BoxPanel(Orientation.Horizontal){
    contents += new Label("Schwierigkeitsgrad: ")
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
    enabled = true
  }

  // JTextPane für Output
  object OutputTextField extends Component {
    override lazy val peer: JTextPane = new JTextPane() {
      setContentType("text/html")
      setEditable(false)
    }
  }


  val InputPanel = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(300, 50) // Beispielgröße
    maximumSize = preferredSize
    minimumSize = preferredSize

    contents += new Label("Versuch:")
    contents += inputTextField
  }


  //--------------------------------------------------

  // Das OutputPanel, das das OutputTextField enthält
  // OutputPanel, das OutputTextField enthält
  val OutputPanel = new BoxPanel(Orientation.Vertical) {
    contents += Component.wrap(OutputTextField.peer)
  }

  val centerPanelSize = new Dimension(300, 300)
  val centerPanel = new Panel {
    preferredSize = centerPanelSize
    minimumSize = centerPanelSize
    maximumSize = centerPanelSize

    peer.setLayout(new GridBagLayout())
    val c = new GridBagConstraints()

    // Konfiguration für das InputPanel
    c.gridx =0
    c.gridy = 0
    c.gridwidth = 1
    c.anchor = GridBagConstraints.NORTH
    c.weightx = 1.0
    peer.add(InputPanel.peer, c)

    // Konfiguration für das OutputPanel innerhalb eines ScrollPane
    c.gridy = 1
    c.weighty = 1.0
    c.fill = GridBagConstraints.BOTH
    val scrollPane = new ScrollPane(Component.wrap(OutputPanel.peer))
    peer.add(scrollPane.peer, c)
  }


  //--------------------
  object newsBoard extends TextArea {
    columns = 20
    rows = 3
    text = "Suche dir ein Spielmodus aus"
    editable = false
    lineWrap = true
    wordWrap = true
  }
  val southPanel = new BorderPanel {
    border = Swing.LineBorder(java.awt.Color.BLACK)
    add(newsBoard, BorderPanel.Position.Center)
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
      level.text = "leicht"
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
      level.text = "mittel"
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
      level.text = "schwer"
      newsBoard.text = "Errate 4 Wörter mit 1 guess bevor die Versuche ausgehen"
      inputTextField.enabled = true
      n = 1

  }

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
      .split("\n") // Teilt den String in Zeilen
      .map { line =>
        val yellowColored = YellowPattern.replaceAllIn(line, m => s"<font color='yellow'>${m.group(1)}</font>")
        val greenColored = GreenPattern.replaceAllIn(yellowColored, m => s"<font color='green'>${m.group(1)}</font>")
        greenColored
      }
      .mkString("<br>") // Fügt die Zeilen mit <br> als Trennzeichen zusammen

    s"<html>$formattedInput</html>"
  }


  private def colorToANSIEscape(color: Color): String = {
    val ANSI_COLOR_ESCAPE = "\u001B[38;2;"
    val ANSI_COLOR_RESET = "\u001B[0m"

    val rgb = color.getRGB
    val r = (rgb >> 16) & 0xFF
    val g = (rgb >> 8) & 0xFF
    val b = rgb & 0xFF

    s"$ANSI_COLOR_ESCAPE$r;$g;$b" + "m" + ANSI_COLOR_RESET // ANSI escape sequence for setting text color and resetting
  }

  pack()
  centerOnScreen()
  open()


}
