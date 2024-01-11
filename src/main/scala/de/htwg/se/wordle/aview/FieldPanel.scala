package de.htwg.se.wordle.aview
import scala.swing.*
import scala.swing.event.*
import java.awt.Image.*
import javax.imageio.ImageIO
import java.io.File
import de.htwg.se.wordle.controller.ControllerInterface

import java.awt.image.BufferedImage
class FieldPanel()extends FlowPanel {

  var PanelMap = Map.empty[Int, BoxPanel]

  val basePath = "texturengui/buchstaben/"

  val YellowPattern = """\u001B\[33m([^\u001B]+)\u001B\[0m""".r
  val GreenPattern = """\u001B\[32m([^\u001B]+)\u001B\[0m""".r
  val unterstrichPattern = """_""".r
  val letterPattern = """(?<!gelb\$|grun\$)[a-z]""".r

  def fieldPanel(Gamefield:String):BoxPanel={
    val fieldpanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
      contents ++= Gamefield.split("§").flatMap { line =>
        if (line == "\n") {
          Seq(new Label(""))
        } else {
          Seq(CharToPic(line))
        }
      }
    }
    fieldpanel
  }
  
  var boardPanel:BoxPanel= new BoxPanel(Orientation.Horizontal){
    PanelMap.values.foreach(panel => contents += panel)
  }

  def getPanel():BoxPanel={
    boardPanel
  }


  def CharToPic(letter:String):Panel= new Panel{
    //Das char filtern ob farbig oder _
    val image = loadImage(letter)
  }



  def loadImage(letter:String):BufferedImage={
    val file = basePath + letter + ".png"
    try {
      ImageIO.read(new File(file))
    } catch {
      case e: Exception =>
        // Fehlerbehandlung, falls das Bild nicht geladen werden kann
        println(s"Fehler beim Laden des Bildes: $file")
        new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB) // Ein leeres Bild zurückgeben
    }
  }

  def loadGamefield(text:String):BoxPanel={
    val parts = text.map {
      case '\n' => "\n§||" // Wenn das Zeichen ein Komma ist, ersetzen Sie es durch ein Leerzeichen
      case other => other // Andernfalls behalten Sie das Zeichen bei
    }.mkString("")
    val input = parts.split("||").map { line =>
        val unterstrich = unterstrichPattern.replaceAllIn(line, m=>"2§")
        val yellowColored = YellowPattern.replaceAllIn(unterstrich, m => s"${m}gelb§") // Dunkleres Gelb
        val greenColored = GreenPattern.replaceAllIn(yellowColored, m => s"${m}grun§")
        val buchstabe = letterPattern.replaceAllIn(greenColored, m=> s"${m}§")
      }
      .mkString("")

    fieldPanel(input)
  }

  def loadGameBoard(text:String):Unit={
    var n= 0
    text.split("\n\n").map{ line =>
      n = n+1
      PanelMap += n -> loadGamefield(line)
    }
  }
}
