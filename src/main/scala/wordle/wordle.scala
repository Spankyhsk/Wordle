
package wordle

object wordle {
  def main(args: Array[String]): Unit = {
    val controller = new controll()
    val testwort = "A"
    controller.start(testwort)
  }
}

