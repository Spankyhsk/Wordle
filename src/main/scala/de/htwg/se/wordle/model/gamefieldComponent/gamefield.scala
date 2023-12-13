package de.htwg.se.wordle.model.gamefieldComponent


  case class gamefield() extends GamefieldInterface[String] {

    var map = Map.empty[Int, String]

    def getMap():Map[Int, String]={
      map
    }
    

    def set(key: Int, feedback: String): Unit = {
      map = map + (key -> feedback)
    }
    def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit={}

    def buildGamefield(n: Int, key: Int, value: String): Unit = {
      map += (key -> value)
      if (key < n) buildGamefield(n, key + 1, value)
      
    }
    def buildGameboard(n: Int, key: Int): Unit={}
    

    override def toString: String = {
      var gamefield = ""
      val mapValues = map.values
      mapValues.foreach(value => gamefield += value + "\n")
      gamefield

    }
		

  }

  case class gameboard() extends GamefieldInterface[GamefieldInterface[String]] {
    var map = Map.empty[Int, GamefieldInterface[String]]
    def getMap():Map[Int, GamefieldInterface[String]]={
      map
    }

    override def set(key: Int, feedback: String): Unit = {}

    def setR(n: Int, key: Int, feedback: Map[Int, String]): Unit = {
      map(n).set(key, feedback(n))
      if (n < map.size) setR(n + 1, key, feedback)
    }

    override def buildGamefield(n: Int, key: Int, value: String): Unit = {}

    def buildGameboard(n: Int, key: Int): Unit = {
      map += (key -> new gamefield())
      if (key < n) buildGameboard(n, key + 1)
    }
    
    
    override def toString: String = {
      var gameboard = ""
      val mapValues = map.values
      mapValues.foreach(value => gameboard += value.toString + "\n")
      gameboard

    }

  }
  

