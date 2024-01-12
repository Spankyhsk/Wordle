package de.htwg.se.wordle.aview
import scala.swing.*
import scala.swing.event.*
import java.awt.Image.*
import javax.imageio.ImageIO
import java.io.File
import de.htwg.se.wordle.controller.ControllerInterface

import java.awt.Color
import java.awt.image.BufferedImage
import javax.swing.JTextPane
class FieldPanel()extends FlowPanel {

  var PanelMap = Map.empty[Int, BoxPanel]


  object FieldTextField extends Component {
    override lazy val peer: JTextPane = new JTextPane() {
      setContentType("text/html")

      setEditable(false)
      setBackground(new Color(0, 0, 0, 0)) // Hintergrund transparent machen
    }
  }

  def loadfieldPanel(Gamefield:String):BoxPanel={
    val fieldpanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
      contents += Component.wrap(FieldTextField.peer)
      }
    val filteredAndColoredText = filterAndColor(Gamefield)
    FieldTextField.peer.setText(Gamefield)
    FieldTextField.peer.setCaretPosition(0)
    fieldpanel
  }
  
  var boardPanel:BoxPanel= new BoxPanel(Orientation.Horizontal){
    PanelMap.values.foreach(panel => contents += panel)
  }

  def getPanel():BoxPanel={
    boardPanel
  }


  def loadGameBoard(text:String):Unit={
   
    var n= 0
    text.split("\n\n").map{ line =>
      n = n+1
      PanelMap += n -> loadfieldPanel(line)
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

    s"<html><body style='font-family:earwigFactoryFont; font-size:60pt;'>$formattedInput</body></html>"
  }
}
