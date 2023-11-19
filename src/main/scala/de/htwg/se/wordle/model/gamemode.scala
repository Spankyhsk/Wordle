package de.htwg.se.wordle.model

import scala.util.Random


object gamemode{


  /*var state = gamemode1
  val wordlength = 2
  def handle(e:Int) = {
    e match{
      case 1 => state = gamemode1
      case 2 => state = gamemode2
      case 3 => state = gamemode3

      state
    }

  }

  def gamemode1={
    val targetword = selectRandomWord(word(wordlength))
    val limit = 3
  }

  def gamemode2={
    val targetword = Map(
      1 -> selectRandomWord(word(wordlength)),
      2 -> selectRandomWord(word(wordlength))
    )
    val limit = 3
  }

  def gamemode3 = {
    val targetword = Map(
      1 -> selectRandomWord(word(wordlength)),
      2 -> selectRandomWord(word(wordlength)),
      3 -> selectRandomWord(word(wordlength)),
      4 -> selectRandomWord(word(wordlength))
    )
    val limit = 3
  }*/
  trait State{
    def handle(e:Int):State

    def getTargetword():Map[Int, String]
    def getLimit():Int
  }



  case class gamemode1()extends State {
    val wordObject = new word()
    val wordlength = 2
    val targetword= Map(1->selectRandomWord(wordObject.words(wordlength)))
    val limit = 3


    override def handle(e: Int): State = {
      e match {
        case 1 => gamemode1()
        case 2 => gamemode2()
        case 3 => gamemode3()
      }
    }
    override def getTargetword():Map[Int, String]={
      targetword
    }

    override def getLimit():Int={
      limit
    }
  }

  case class gamemode2()extends State{
    val wordObject = new word()
    val wordlength = 2
    val targetword = Map(
      1 -> selectRandomWord(wordObject.words(wordlength)),
      2 -> selectRandomWord(wordObject.words(wordlength))
    )
    val limit = 3

    override def handle(e: Int): State = {
      e match {
        case 1 => gamemode1()
        case 2 => gamemode2()
        case 3 => gamemode3()
      }
    }

    override def getTargetword(): Map[Int, String] = {
      targetword
    }

    override def getLimit(): Int = {
      limit
    }
  }

  case class gamemode3()extends State{
    val wordObject = new word()
    val wordlength = 2
    val targetword = Map(
      1 -> selectRandomWord(wordObject.words(wordlength)),
      2 -> selectRandomWord(wordObject.words(wordlength)),
      3 -> selectRandomWord(wordObject.words(wordlength)),
      4 -> selectRandomWord(wordObject.words(wordlength))
    )
    val limit = 3

    override def handle(e: Int): State = {
      e match {
        case 1 => gamemode1()
        case 2 => gamemode2()
        case 3 => gamemode3()
      }
    }

    override def getTargetword(): Map[Int, String] = {
      targetword
    }

    override def getLimit(): Int = {
      limit
    }
  }

  var state: State = gamemode1()//targetword und limit nimmt die werte als erstes

  def handle(e: Int) = state = state.handle(e)

  def selectRandomWord(wordArray: Array[String]): String = {
    Random.shuffle(wordArray.toList).head
  }

  case class word() {

    val words= Map(
      1 -> Array("A", "B", "C", "D", "E"),
      2 -> Array("AB", "BC", "CD", "DE", "EF"),
      3 -> Array("ABC", "BCD", "CDE", "DEF", "EFG"),
      4 -> Array("ABCD", "BCDE", "CDEF", "DEFG", "EFGH"),
      5 -> Array("ABCDE", "BCDEF", "CDEFG", "DEFGH", "EFGHI")
    )


  }



}
