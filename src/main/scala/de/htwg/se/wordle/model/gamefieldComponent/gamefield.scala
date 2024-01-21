package de.htwg.se.wordle.model.gamefieldComponent

//=====================================================================

              //!!!GAMEFIELD!!!

//=====================================================================

case class gamefield() extends GamefieldInterface[String] {
  var Field = Map.empty[Int, String]
  def getMap(): Map[Int, String] = {
    Field
  }
  def setMap(boardmap: Map[Int, Map[Int, String]]): Unit = {
  }
  def SetMapper(field: Map[Int, String]): Unit = {
    Field = field
  }
  def set(key: Int, feedback: String): Unit = {
    Field = Field.updated(key, feedback)
  }
  def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit = {}
  def buildGamefield(n: Int, key: Int, value: String): Unit = {
    Field += (key -> value)
    if (key < n) buildGamefield(n, key + 1, value)

  }
  def buildGameboard(n: Int, key: Int): Unit = {}
  override def toString: String = {
    Field.toSeq.sortBy(_._1).map(_._2).mkString("\n")
  }
  override def reset(): Unit = {
    Field = Map.empty[Int, String]
  }
}

//=====================================================================

                //!!!GAMEBOARD!!!

//=====================================================================

case class gameboard() extends GamefieldInterface[GamefieldInterface[String]] {
  var Board = Map.empty[Int, GamefieldInterface[String]]
  def getMap(): Map[Int, GamefieldInterface[String]] = {
    Board
  }
  def setMap(boardmap: Map[Int, Map[Int, String]]): Unit = {
    setMapR(boardmap.size, 1, boardmap)
  }
  def setMapR(n: Int, key: Int, boardmap: Map[Int, Map[Int, String]]): Unit = {
    val GameField = new gamefield()
    GameField.SetMapper(boardmap(key))
    Board += (key -> GameField)
    if (key < n) setMapR(n, key + 1, boardmap)
  }
  override def set(key: Int, feedback: String): Unit = {}
  def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit = {
    Board.get(n).foreach(_.set(key, feedback.getOrElse(n, "")))
    if (n < Board.size) setR(n + 1, key, feedback)
  }
  override def buildGamefield(n: Int, key: Int, value: String): Unit = {}
  def buildGameboard(n: Int, key: Int): Unit = {
    Board += (key -> new gamefield())
    if (key < n) buildGameboard(n, key + 1)
  }
  override def reset(): Unit = {
    Board = Map.empty[Int, GamefieldInterface[String]]
  }
  override def toString: String = {
    val gamefieldsString = Board.toSeq.sortBy(_._1).map { case (_, field) => field.toString }
    gamefieldsString.mkString("\n\n") // Zwei Zeilenumbrüche für die Trennung zwischen den Gamefields
  }
}
  

