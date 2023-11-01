
package controller
import model.attempt
import scala.util.Random

case class controll(val attempt: attempt) {

  val limit = attempt.x
  val targetword = attempt.targetword

  def count(n:Int):Boolean={

    if(n < limit) true else false

  }


  def evaluateGuess(targetWord: String, guess: String): Unit = {
    val feedback = guess.zipWithIndex.map {
      case (g, i) if g == targetWord(i) => "\u001B[32m" + g + "\u001B[0m" //Tupel mit (buchstabe, index)
      case (g, _) if targetWord.contains(g) => "\u001B[33m" + g + "\u001B[0m"
      case (g, _) => g.toString
    }.mkString("")

    println(s"Dein Tipp: $feedback")
  }




}
