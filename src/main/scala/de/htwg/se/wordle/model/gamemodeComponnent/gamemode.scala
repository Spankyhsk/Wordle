package de.htwg.se.wordle.model.gamemodeComponnent

import scala.util.Random

case class gamemode1(wordObject: Word) extends GamemodeInterface {
  def this() = this(new Word())
  
  val wordlength = 2
  var targetword = Map(1 -> wordObject.selectRandomWord(wordObject.words(wordlength)))
  var limit = 3

  

  override def getTargetword(): Map[Int, String] = {
    targetword
  }

  override def getLimit(): Int = {
    limit
  }

  override def getWordList(): Array[String] = {
    wordObject.words(wordlength)
  }
  
  def setTargetWord(targetWordMap:Map[Int, String]): Unit = {
    targetword = targetWordMap
  }
  
  def setLimit(Limit:Int): Unit = {
    limit = Limit
  }

  override def toString(): String = {
    targetword.map { case (key, value) => s"Wort $key: $value" }.mkString(" ")
  }
}

case class gamemode2(wordObject: Word) extends GamemodeInterface {
  def this() = this(new Word())
  val wordlength = 2
  var targetword = Map(
    1 -> wordObject.selectRandomWord(wordObject.words(wordlength)),
    2 -> wordObject.selectRandomWord(wordObject.words(wordlength))
  )
  var limit = 4
  

  override def getTargetword(): Map[Int, String] = {
    targetword
  }

  override def getLimit(): Int = {
    limit
  }

  override def getWordList(): Array[String] = {
    wordObject.words(wordlength)
  }

  def setTargetWord(targetWordMap: Map[Int, String]): Unit = {
    targetword = targetWordMap
  }

  def setLimit(Limit: Int): Unit = {
    limit = Limit
  }

  override def toString(): String = {
    targetword.map { case (key, value) => s"Wort $key: $value" }.mkString(" ")
  }
}

case class gamemode3(wordObject: Word) extends GamemodeInterface {
  def this() = this(new Word())
  val wordlength = 2
  var targetword = Map(
    1 -> wordObject.selectRandomWord(wordObject.words(wordlength)),
    2 -> wordObject.selectRandomWord(wordObject.words(wordlength)),
    3 -> wordObject.selectRandomWord(wordObject.words(wordlength)),
    4 -> wordObject.selectRandomWord(wordObject.words(wordlength))
  )
  var limit = 6
  

  override def getTargetword(): Map[Int, String] = {
    targetword
  }

  override def getLimit(): Int = {
    limit
  }

  override def getWordList(): Array[String] = {
    wordObject.words(wordlength)
  }

  def setTargetWord(targetWordMap: Map[Int, String]): Unit = {
    targetword = targetWordMap
  }

  def setLimit(Limit: Int): Unit = {
    limit = Limit
  }

  override def toString(): String = {
    targetword.map { case (key, value) => s"Wort $key: $value" }.mkString(" ")
  }
}



object gamemode{
  import play.api.libs.json._
  
  var state: GamemodeInterface = gamemode1(new Word())//targetword und limit nimmt die werte als erstes

  def apply(e: Int) = {
    e match {
      case 1 => gamemode1(new Word())
      case 2 => gamemode2(new Word())
      case 3 => gamemode3(new Word())
    }
  }
 

}
