package de.htwg.se.wordle.model.gamefieldComponent


case class gamefield() extends GamefieldInterface[String] {

  var map = Map.empty[Int, String]

  def getMap(): Map[Int, String] = {
    map
  }

  def setMap(boardmap: Map[Int, Map[Int, String]]): Unit = {
  }

  def SetMapper(field: Map[Int, String]): Unit = {
    map = field
  }


  def set(key: Int, feedback: String): Unit = {
    map = map.updated(key, feedback)
  }

  def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit = {}

  def buildGamefield(n: Int, key: Int, value: String): Unit = {
    map += (key -> value)
    if (key < n) buildGamefield(n, key + 1, value)

  }

  def buildGameboard(n: Int, key: Int): Unit = {}


  override def toString: String = {
    map.toSeq.sortBy(_._1).map(_._2).mkString("\n")
  }

  override def reset(): Unit = {
    map = Map.empty[Int, String]
    // Fügen Sie hier weitere Schritte zur Neuinitialisierung hinzu, falls benötigt
  }


}

case class gameboard() extends GamefieldInterface[GamefieldInterface[String]] {
  var map = Map.empty[Int, GamefieldInterface[String]]

  def getMap(): Map[Int, GamefieldInterface[String]] = {
    map
  }

  def setMap(boardmap: Map[Int, Map[Int, String]]): Unit = {
    setMapR(boardmap.size, 1, boardmap)
  }

  def setMapR(n: Int, key: Int, boardmap: Map[Int, Map[Int, String]]): Unit = {
    val GameField = new gamefield()
    GameField.SetMapper(boardmap(key))
    map += (key -> GameField)
    if (key < n) setMapR(n, key + 1, boardmap)
  }

  override def set(key: Int, feedback: String): Unit = {}

  def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit = {
    map.get(n).foreach(_.set(key, feedback.getOrElse(n, "")))
    if (n < map.size) setR(n + 1, key, feedback)
  }


  override def buildGamefield(n: Int, key: Int, value: String): Unit = {}

  def buildGameboard(n: Int, key: Int): Unit = {
    map += (key -> new gamefield())
    if (key < n) buildGameboard(n, key + 1)
  }

  override def reset(): Unit = {
    map = Map.empty[Int, GamefieldInterface[String]]
    // Hier können Sie weitere Schritte zur Neuinitialisierung durchführen
  }


  override def toString: String = {
    val gamefieldsString = map.toSeq.sortBy(_._1).map { case (_, field) => field.toString }
    gamefieldsString.mkString("\n\n") // Zwei Zeilenumbrüche für die Trennung zwischen den Gamefields
  }


}
  

