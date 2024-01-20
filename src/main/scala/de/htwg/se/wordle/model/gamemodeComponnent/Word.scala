package de.htwg.se.wordle.model.gamemodeComponnent

import scala.util.Random

case class Word(){

  val words= Map(
    1 -> Array("A", "B", "C", "D", "E"),
    2 -> Array("AB", "BC", "CD", "DE", "EF"),
    3 -> Array("ABC", "BCD", "CDE", "DEF", "EFG"),
    4 -> Array("ABCD", "BCDE", "CDEF", "DEFG", "EFGH"),
    5 -> Array("TISCH",
      "BLUME",
      "KARTE",
      "MUSIK",
      "LAMPE",
      "STUHL",
      "EIMER",
      "BLICK",
      "HUNDE",
      "ESSEN",
      "VOGEL",
      "GABEL",
      "BÄUME",
      "ADLER",
      "HAFEN",
      "KÖNIG",
      "KREIS",
      "GLANZ",
      "OZEAN",
      "NADEL",
      "PFOTE",
      "SPIEL",
      "RÄUME",
      "LACHS",
      "STEIN",
      "WOLKE",
      "EICHE",
      "STERN",
      "BLATT",
      "ANGEL",
      "ABEND",
      "BIENE",
      "FISCH",
      "SONNE",
      "SPEER",
      "MAUER",
      "FLUSS",
      "SCHUH",
      "TIGER",
      "ZEBRA",
      "ZUNGE",
      "AMSEL",
      "FJORD",
      "WICHT",
      "MESSE",
      "PISTE",
      "ZOBEL",
      "QUARK",
      "NONNE",
      "NUDEL",
      "KATZE")
  )

  def selectRandomWord(wordArray: Array[String]): String = {
    Random.shuffle(wordArray.toList).head
  }


}
