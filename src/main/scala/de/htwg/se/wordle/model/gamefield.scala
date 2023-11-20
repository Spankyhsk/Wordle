package de.htwg.se.wordle.model
object gamefield {
  trait Component{
    def set(key:Int, feedback:String):Unit
    def buildGamefield(n: Int, key: Int, value: String): Unit
    def toString: String
		def setWin:Boolean
		def getWin:Boolean
  }
  case class gamefield()extends Component {

    var map = Map.empty[Int, String]
		var win = False

    def set(key: Int, feedback: String): Unit = {
      map = map + (key -> feedback)
    }

    def buildGamefield(n: Int, key: Int, value: String): Unit = {
      map += (key -> value)
      if (key < n) buildGamefield(n, key + 1, value)


    }

    override def toString: String = {
      var gamefield = ""
      val mapValues = map.values
      mapValues.foreach(value => gamefield += value + "\n")
      gamefield

    }
		def setWin():Boolean={
			win = true
		}

		def getWin():Boolean={
			win
		}

  }

  case class gameboard() extends Component{
    var map = Map.empty[Int, Component]
		var win = false
    override def set(key:Int, feedback:String):Unit={}
    override def buildGamefield(n: Int, key: Int, value: String): Unit={}

    def buildGameboard(n:Int, key:Int, value:Component): Unit = {
      map += (key -> value)
      if (key < n) buildGameboard(n, key + 1, value)
    }

    def getChilderen(key:Int):Component={
      val children = map(key)
      children
    }

    override def toString: String ={//druckt nur gamefield 2 wenn es 2 gibt // HAT FEHLER
      var gameboard = ""
      val mapValues = map.values
      mapValues.foreach(value => gameboard += value.toString + "\n")
      gameboard

    }
		def setWin():Boolean={
			win = true
		}

		def getWin():Boolean={
			win
		}

  }
}
