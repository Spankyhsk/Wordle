
package wordle


class controll {
  import scala.util.Random

  
  //Random rausgenommen wegen Testbarkeit
  def zufall(): Unit = {
      val array1Buchstab: Array[String] = Array("A", "B", "C", "D")
      val zufallword = array1Buchstab(Random.nextInt(array1Buchstab.length))
    }

  def start(zuloesendesword: String): Unit = {
    val loesungsword = zuloesendesword

    println("Willkommen zu Wordle!")
    input(loesungsword)(0)
/*
    val eingabe = scala.io.StdIn.readLine("Noch eine Runde?[Y]: ")
    if(eingabe == "Y"){
      start("A")    //Nächste Runde beginnt wieder mit "A" als Lösung --> nur zum testen erstmal
    }else{
      println("Auf Wiedersehen")
    }*/

  }

  def input(loesung: String)(n: Int): Unit = {
    if (n < 1) {
      val eingabe = scala.io.StdIn.readLine("Versuch eingeben: ")
      //methode um zu prüfen ob eingabe im array ist
      filter(loesung, eingabe, n)
    } else {
      println(loesung)
      println("Verloren")
      // Zurück zur Main oder beenden
    }
  }

  def filter(loesung: String, eingabe: String, versuche: Int): Unit = {
    if (loesung == eingabe) {
      println(loesung)
      println("Gewonnen")
      // Zurück zur Main oder beenden
    } else {
      //methode für richtige buchstaben
      //methode für
      println("Versuch nicht erfolgreich. Nochmal versuchen.")
      input(loesung)(versuche + 1)
    }
  }
}
