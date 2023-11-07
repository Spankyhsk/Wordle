package de.htwg.se.wordle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.wordle.wordle

class wordspec extends AnyWordSpec with Matchers{
  "word" should{
    "select a random word" in {
      val wordsByLength = Map(
        1 -> Array("A", "B", "C", "D", "E"),
        2 -> Array("AB", "BC", "CD", "DE", "EF"),
        3 -> Array("ABC", "BCD", "CDE", "DEF", "EFG"),
        4 -> Array("ABCD", "BCDE", "CDEF", "DEFG", "EFGH"),
        5 -> Array("ABCDE", "BCDEF", "CDEFG", "DEFGH", "EFGHI")
      )

      val word = wordle.selectRandomWord(wordsByLength(3))
      wordsByLength(3) should contain(word)
    }
  }

}
