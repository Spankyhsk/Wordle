package de.htwg.se.wordle.aview


import de.htwg.se.wordle.controller.ControllerInterface
import de.htwg.se.wordle.util.{ Event, Observer}


import java.awt.event.{ComponentAdapter, ComponentEvent}
import java.awt.Font
import javax.swing.{JPanel, JScrollPane, SwingUtilities}
import scala.swing.*
import scala.swing.event.*
import java.awt.{BorderLayout, Color, GridBagConstraints, GridBagLayout, Image}
import scala.swing.MenuBar.NoMenuBar.revalidate
import javax.swing.ImageIcon


class GUISWING(controll:ControllerInterface) extends Frame with Observer {
  controll.add(this)

  // Fenster-Titel und Skalierbarkeit
  title = "Wordle"
  resizable = true

//=========================================================================

              //!!!Schriften!!!

//=========================================================================

  // Schriftart definieren
  val customFont = new Font("Skia", Font.PLAIN, 14)
  val comicFont: Font = LoadCustomFont.loadFont("texturengui/font/Comicmeneu-Regular.ttf").deriveFont(24F)


//========================================================================

            //!!!UPDATE!!!

//========================================================================

  override def update(e:Event):Unit={
    e match
      case Event.Move =>{
        upgradeOutput()
      }
      case Event.NEW =>{//Hat ein Deadlock oder so bzw wenn man die changestate aufruft
        controll.setVersuche(1)
        NEWSPanel.updateNewsBoardText("Errate die Gesuchten Wörter, innerhalb der Angegeben Versuche")
        inputTextField.enabled = true
        editDoneEventFired = false
      }
      case Event.WIN =>{
        NEWSPanel.updateNewsBoardText(s"Gewonnen! Lösung: ${controll.TargetwordToString()}\n Zum erneuten Spielen Schwierigkeitsgrad aussuchen")
        inputTextField.enabled = false
        editDoneEventFired = true
      }
      case Event.LOSE =>{
        NEWSPanel.updateNewsBoardText(s"Verloren! Lösung:  ${controll.TargetwordToString()}\n Zum erneuten Spielen Schwierigkeitsgrad aussuchen")
        inputTextField.enabled = false
        editDoneEventFired = true

      }
      case Event.UNDO => {
        upgradeOutput()
      }


  }

//=======================================================================================

                //!!!PANELS!!!

//=======================================================================================


//---------------------------------------------------------------------------------------

            //!!!NorthPanel!!!

//---------------------------------------------------------------------------------------

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


  val headlinepanel = new ResizableBannerPanel("texturengui/Wordlebanner2.png")

  peer.addComponentListener(new ComponentAdapter {
    override def componentResized(e: ComponentEvent): Unit = {
      headlinepanel.updateBannerSize(peer.getSize().width)
    }
  })


  val EasymodusButton = new TransparentButton("Leicht")
  val MediummodusButton = new TransparentButton("Mittel")
  val HardmodusButton = new TransparentButton("Schwer")


  val gamemoduspanel = new BoxPanel(Orientation.Horizontal) {
    contents += EasymodusButton
    contents += Swing.HStrut(20) // Fügt einen horizontalen Abstand von 10 Pixeln hinzu
    contents += MediummodusButton
    contents += Swing.HStrut(20) // Fügt einen weiteren horizontalen Abstand von 10 Pixeln hinzu
    contents += HardmodusButton
  }


  val gamemoduspanelMain = new BoxPanel(Orientation.Vertical) { //Methode
    contents += gamemoduspanel

  }

  var northpanel = new BoxPanel(Orientation.Vertical) {
    contents += menuBar
    contents += headlinepanel
    contents += gamemoduspanelMain //muss updatebar sein
  }

//---------------------------------------------------------------------------------------

            //!!!CENTERPANEL!!!

//----------------------------------------------------------------------------------------

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
  } //Klappt

  val centerPanelSize = new Dimension(300, 300) // Festgelegte Größe für das centerPanel

  val scrollPane = new ScrollPane(OutputPanel) {
    border = Swing.EmptyBorder(0, 0, 0, 0)
    override lazy val peer: JScrollPane = new JScrollPane(OutputPanel.peer) with SuperMixin {
      setOpaque(false)
      getViewport.setOpaque(false)
    }
  }

  val centerPanel = new BoxPanel(Orientation.Vertical) {

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

//----------------------------------------------------------------------------------------

            //!!!SouthPANEL!!!

//----------------------------------------------------------------------------------------

  val texturedBackground = new TexturedBackground("texturengui/4rippedpaperneu.png") {
    layout(NEWSPanel.NewsBoardPanel()) = BorderPanel.Position.Center
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
  }

  val southPanel = new BoxPanel(Orientation.Vertical) {
    border = Swing.LineBorder(java.awt.Color.BLACK)
    contents += texturedBackground
    border = Swing.EmptyBorder(0, 0, 0, 0) // Keine sichtbare Grenze
  }

//----------------------------------------------------------------------------------------

            //!!!MainPanel!!!

//----------------------------------------------------------------------------------------

  val backgroundPanel = new BackgroundPanel("texturengui/7background.jpg")
  northpanel.peer.setOpaque(false)
  centerPanel.peer.setOpaque(false)
  southPanel.peer.setOpaque(false)
  inputTextField.peer.setOpaque(false)
  gamemoduspanelMain.peer.setOpaque(false)
  headlinepanel.peer.setOpaque(false)
  gamemoduspanel.peer.setOpaque(false)
  InputPanel.peer.setOpaque(false)
  OutputPanel.peer.setOpaque(false)

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
  centerOnScreen()
  open()
  peer.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH)// Fenster maximieren


//=======================================================================================

  //!!!REACTION!!!

//=======================================================================================

  var editDoneEventFired = false
  listenTo(inputTextField, EasymodusButton, MediummodusButton, HardmodusButton)
  reactions += {
    case EditDone(inputTextField) =>
      val guess = controll.GuessTransform(inputTextField.text)

      if (!editDoneEventFired) {
        if (controll.controllLength(guess.length) && controll.controllRealWord(guess)) {
          if (!controll.areYouWinningSon(guess) && controll.count()) {
            controll.set(controll.getVersuche(), controll.evaluateGuess(guess))
            controll.setVersuche(controll.getVersuche() + 1)
          } else {
            controll.set(controll.getVersuche(), controll.evaluateGuess(guess))
          }
        } else {
          NEWSPanel.updateNewsBoardText("Falsche Eingabe")
        }
      }
      resetInputField()


    case ButtonClicked(EasymodusButton) =>

      upgradegamemoduspanel(EasymodusButton)
      controll.changeState(1)
      controll.createGameboard()
      controll.createwinningboard()

    case ButtonClicked(MediummodusButton) =>

      upgradegamemoduspanel(MediummodusButton)
      controll.changeState(2)
      controll.createGameboard()
      controll.createwinningboard()

    case ButtonClicked(HardmodusButton) =>

      upgradegamemoduspanel(HardmodusButton)
      controll.changeState(3)
      controll.createGameboard()
      controll.createwinningboard()

  }

//========================================================================================

            //!!!Kleinere Methoden!!!

//========================================================================================

  def upgradegamemoduspanel(clickedButton: Button): Unit = {
    updateButtonColors(clickedButton: Button)
    gamemoduspanelMain.contents.clear()
    gamemoduspanelMain.contents += gamemoduspanel
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

  def resetInputField(): Unit = {
    javax.swing.SwingUtilities.invokeLater(new Runnable {
      def run(): Unit = {
        inputTextField.text = ""
      }
    })
  }

  def upgradeOutput(): Unit = {
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

