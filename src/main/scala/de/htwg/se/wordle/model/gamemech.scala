package de.htwg.se.wordle.model

  case class gamemech() {
    var winningboard = Map.empty[Int, Boolean]
    def count(n: Int, limit: Int): Boolean = {
      if (n < limit) true else false
    }

    def compareTargetguess(targetword: String, guess: String): Boolean = { //als Stragy-panel
      if (guess == targetword) true else false
    }

    def evaluateGuess(targetWord: String, guess: String): String = {
      val feedback = guess.zipWithIndex.map {
        case (g, i) if g == targetWord(i) => "\u001B[32m" + g + "\u001B[0m" //Tupel mit (buchstabe, index)
        case (g, _) if targetWord.contains(g) => "\u001B[33m" + g + "\u001B[0m"
        case (g, _) => g.toString
      }.mkString("")
      feedback
    }

    def buildwinningboard(n: Int, key: Int): Unit = {
      winningboard += (key -> false)
      if (key < n) buildwinningboard(n, key + 1)
    }

    def setWin(key: Int): Unit = {
      winningboard = winningboard + (key -> true)
    }

    def getWin(key: Int): Boolean = {
      winningboard(key)
    }

    def areYouWinningSon(): Boolean = {
      val allValuesEqualTrue: Boolean = winningboard.values.forall(_ == true)
      allValuesEqualTrue
    }

    //Methode die Wortlänge prüft von guess

    //Methode die Prüft ob guess ein echtes wort ist (in unser Map der Wörter)

    //Methode die input in groß buchstaben ändert(nicht wirklich notwendig aber wer weiß)

  }


