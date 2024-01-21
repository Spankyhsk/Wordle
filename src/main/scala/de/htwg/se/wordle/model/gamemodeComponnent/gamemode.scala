package de.htwg.se.wordle.model.gamemodeComponnent

//====================================================================

              //!!!Gamemode1!!!

//====================================================================
case class gamemode1(wordObject: Word) extends GamemodeInterface {
  def this() = this(new Word())
  
  val wordlength = 5
  var targetword = Map(1 -> wordObject.selectRandomWord(wordObject.words(wordlength)))
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
  def setTargetWord(targetWordMap:Map[Int, String]): Unit = {
    targetword = targetWordMap
  }
  def setLimit(Limit:Int): Unit = {
    limit = Limit
  }
  override def toString(): String = {
    targetword.map { case (key, value) => s"Wort$key: $value" }.mkString(" ")
  }
}

//====================================================================

                    //!!!Gamemode2!!!

//====================================================================

case class gamemode2(wordObject: Word) extends GamemodeInterface {
  def this() = this(new Word())
  val wordlength = 5
  var targetword = Map(
    1 -> wordObject.selectRandomWord(wordObject.words(wordlength)),
    2 -> wordObject.selectRandomWord(wordObject.words(wordlength))
  )
  var limit = 7
  

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
    targetword.map { case (key, value) => s"Wort$key: $value" }.mkString(", ")
  }
}

//====================================================================

                      //!!!Gamemode3!!!

//====================================================================


case class gamemode3(wordObject: Word) extends GamemodeInterface {
  def this() = this(new Word())
  val wordlength = 5
  var targetword = Map(
    1 -> wordObject.selectRandomWord(wordObject.words(wordlength)),
    2 -> wordObject.selectRandomWord(wordObject.words(wordlength)),
    3 -> wordObject.selectRandomWord(wordObject.words(wordlength)),
    4 -> wordObject.selectRandomWord(wordObject.words(wordlength))
  )
  var limit = 8
  

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
    targetword.map { case (key, value) => s"Wort$key: $value" }.mkString(", ")
  }
}



object gamemode{
  
  var state: GamemodeInterface = gamemode1(new Word())

  def apply(e: Int) = {
    e match {
      case 1 => gamemode1(new Word())
      case 2 => gamemode2(new Word())
      case 3 => gamemode3(new Word())
    }
  }
 

}
