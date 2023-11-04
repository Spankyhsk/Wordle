package de.htwg.se.wordle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import src.util.wordle

class wordspec extends AnyWordSpec with Matchers{
  "word" should{
    "select a random word" in {
      val wordsByLength = Map(
        1 -> Array("a", "b", "c", "d", "e"),
        2 -> Array("ab", "bc", "cd", "de", "ef"),
        3 -> Array("abc", "bcd", "cde", "def", "efg"),
        4 -> Array("abcd", "bcde", "cdef", "defg", "efgh"),
        5 -> Array("abcde", "bcdef", "cdefg", "defgh", "efghi")
      )

      val word = wordle.selectRandomWord(wordsByLength(3))
      wordsByLength(3) should contain(word)
    }
  }

}
