package de.htwg.se.wordle.aview
import scala.swing.*
import scala.swing.event.*
import java.awt.Image.*
import javax.imageio.ImageIO
import java.io.File
import de.htwg.se.wordle.controller.ControllerInterface

import java.awt.image.BufferedImage
class FieldPanel()extends FlowPanel {

  var Gamefield = ""

  val basePath = "texturengui/buchstaben/"

  val YellowPattern = """\u001B\[33m([^\u001B]+)\u001B\[0m""".r
  val GreenPattern = """\u001B\[32m([^\u001B]+)\u001B\[0m""".r
  val unterstrichPattern = """_""".r
  val letterPattern = """(?<!gelb\$|grun\$)[a-z]""".r

  val fieldPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents ++= Gamefield.split("§").flatMap { line =>
      if (line == "\n") {
        Seq(new Label(""))
      } else {
        Seq(CharToPic(line))
      }
    }
  }
  
  val boardPanel:BoxPanel= new BoxPanel(Orientation.Horizontal){
    
  }




  def CharToPic(letter:String):Panel= new Panel{
    //Das char filtern ob farbig oder _
    val image = loadImage(letter)
  }



  def loadImage(letter:String):BufferedImage={
    val file = basePath + letter +".png"
    ImageIO.read(new File(file))
  }

  def loadGamefield(text:String):Unit={
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
    Gamefield = input
  }

  def loadGameBoard(text:String):Unit={
    text.split("\n\n").map{ line =>
      loadGamefield(line)
    }
  }
}
