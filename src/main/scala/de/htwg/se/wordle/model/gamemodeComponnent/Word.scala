package de.htwg.se.wordle.model.gamemodeComponnent

import scala.util.Random

case class Word(){

  val words= Map(
    1 -> Array("A", "B", "C", "D", "E"),
    2 -> Array("AB", "BC", "CD", "DE", "EF"),
    3 -> Array("ABC", "BCD", "CDE", "DEF", "EFG"),
    4 -> Array("ABCD", "BCDE", "CDEF", "DEFG", "EFGH"),
    5 -> Array("ABCDE", "BCDEF", "CDEFG", "DEFGH", "EFGHI")
  )

  def selectRandomWord(wordArray: Array[String]): String = {
    Random.shuffle(wordArray.toList).head
  }


}
