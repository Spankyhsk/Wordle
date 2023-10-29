import scala.util.Random



def start(): Unit = {
  val array1Buchstab: Array[String] = Array("A", "B", "C", "D")
  val loesungsword = array1Buchstab(Random.nextInt(array1Buchstab.length))

  println("Willkommen zu Wordle!")
  input(loesungsword)(0)

  val eingabe = scala.io.StdIn.readLine("Noch eine Runde?[Y]: ")
  if(eingabe == "Y"){
    start()
  }else{
    println("Auf Wiedersehen")
  }

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
