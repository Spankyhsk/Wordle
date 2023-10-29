package src
package util
import controller.controll

object wordle {
  def main(args: Array[String]): Unit = {
    val controller = new controll()
    val testwort = "A"
    controller.start(testwort)
  }
}

