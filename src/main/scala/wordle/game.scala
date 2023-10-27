package wordle

object game {
  import scala.util.Random

  def main(args: Array[String]): Unit = {
    val wordsByLength = Map(
      1 -> Array("a", "b", "c", "d", "e"),
      2 -> Array("ab", "bc", "cd", "de", "ef"),
      3 -> Array("abc", "bcd", "cde", "def", "efg"),
      4 -> Array("abcd", "bcde", "cdef", "defg", "efgh"),
      5 -> Array("abcde", "bcdef", "cdefg", "defgh", "efghi")
    )

    val wordLength = askForWordLength()
    val maxAttempts = askForMaxAttempts()

    val targetWord = selectRandomWord(wordsByLength(wordLength))
    val initialDisplay = "_" * wordLength
    playWordle(targetWord, initialDisplay, maxAttempts)
  }

  def askForWordLength(): Int = {
    println("Wähle eine Wortlänge: (1 bis 5): ")
    scala.io.StdIn.readInt()
  }

  def askForMaxAttempts(): Int = {
    println("Wähle die Anzahl deiner Versuche: ")
    scala.io.StdIn.readInt()
  }

  def selectRandomWord(wordArray: Array[String]): String = {
    Random.shuffle(wordArray.toList).head
  }

  def playWordle(targetWord: String, display: String, attemptsLeft: Int): Unit = {
    if (display == targetWord) {
      println(s"Glückwunsch! Du hast das Wort erraten: $targetWord")
    } else if (attemptsLeft == 0) {
      println(s"Sorry, du hast kein Versuch mehr. Das Wort war: $targetWord")
    } else {
      val hiddenWord = "_" * targetWord.length
      println(s"Wortlänge: $hiddenWord")
      println(s"Verbleibende Versuche: $attemptsLeft")
      val guess = scala.io.StdIn.readLine()
      evaluateGuess(targetWord, hiddenWord, guess)
      if (guess == targetWord) {
        println(s"Glückwunsch! Du hast das Wort erraten: $targetWord")
      } else {
        playWordle(targetWord, display, attemptsLeft - 1)
      }
    }
  }

  def evaluateGuess(targetWord: String, display: String, guess: String): Unit = {
    val feedback = guess.zipWithIndex.map {
      case (g, i) if g == targetWord(i) => "\u001B[32m" + g + "\u001B[0m"   //Tupel mit (buchstabe, index)
      case (g, _) if targetWord.contains(g) => "\u001B[33m" + g + "\u001B[0m"
      case (g, _) => g.toString
    }.mkString("")

    println(s"Dein Tipp: $feedback")
  }
}
