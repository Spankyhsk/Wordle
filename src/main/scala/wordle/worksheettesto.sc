
object wordle {
  //test123
  import scala.util.Random


  def main(args: Array[String]): Unit = {


    val array1Buchstab: Array[String] = Array("A", "B", "C", "D")
    val loesungsword = array1Buchstab(Random.nextInt(array1Buchstab.length))

    println("Willkommen zu Wordle!")
    input(loesungsword)(0)
  }


  def input(loesung: String)(n: Int): Unit = {
    if (n < 1) {
      val eingabe = scala.io.StdIn.readLine("Versuch eingeben: ")
      filter(loesung, eingabe, n)
    } else {
      println(loesung)
      println("Verloren")
      // Zurück zur main oder beenden
    }
  }

  def filter(loesung: String, eingabe: String, versuche: Int): Unit = {
    if (loesung == eingabe) {
      println(loesung)
      println("Gewonnen")
      // Zurück zur main oder beenden
    } else {
      println("Versuch nicht erfolgreich. Nochmal versuchen.")
      input(loesung)(versuche + 1)
    }
  }



}
