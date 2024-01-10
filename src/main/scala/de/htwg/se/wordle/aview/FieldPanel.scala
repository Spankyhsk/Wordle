package de.htwg.se.wordle.aview
import scala.swing.*
import scala.swing.event.*
import java.awt.Image.*
import javax.imageio.ImageIO
import java.io.File
import de.htwg.se.wordle.controller.ControllerInterface

import java.awt.image.BufferedImage
class FieldPanel(controll:ControllerInterface)extends FlowPanel {

  var Gamefield = ""

  val basePath = "texturengui/buchstaben/"

  val YellowPattern = """\u001B\[33m([^\u001B]+)\u001B\[0m""".r
  val GreenPattern = """\u001B\[32m([^\u001B]+)\u001B\[0m""".r

  val fieldPanel = new BoxPanel(Orientation.Vertical){
    contents ++= Gamefield.flatMap{ char =>
      if(char == '\n'){
        Seq(new Label(""))
      }else{
        Seq(CharToPic(char))
      }
    }
  }

  def getColor(char: String): String = {
    // Hier implementierst du die Logik zum Extrahieren der Farbcodierung aus dem Zeichen
    // Beispiel: Annahme, dass die Farbcodierung in den letzten zwei Zeichen des Zeichens steht
    if (char.length >= 2) char.substring(char.length - 2) else "default"
  }


  def CharToPic(letter:String):Panel= new Panel{
    //Das char filtern ob farbig oder _
    val color = getColor(letter)
    val image = loadImage(letter, color)
  }



  def loadImage(letter:String, color: String):BufferedImage={
    val file = basePath + s"$letter-$color.png"
    ImageIO.read(new File(file))
  }

  def loadGamefield(text:String):Unit={
    Gamefield = text
  }

  def loadGameBoard(text:String):Unit={
    text.flatMap{char =>
      //gamefield im string splitten mit \n\n und dann jeweils in loadGamefield einsetzen
    }

  }
}
