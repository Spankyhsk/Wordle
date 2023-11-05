package de.htwg.se.wordle.model

case class gamefield(){

  var map = Map.empty[Int, String]
  def set(key:Int, feedback:String):Unit={
    map = map +(key -> feedback)
  }

  def buildGamefield(n:Int,key:Int, value:String): Unit = {
    map += (key->value)
    if(key<n)buildGamefield(n, key+1, value)


  }

  override def toString: String ={
    var gamefield = ""
    val mapValues = map.values
    mapValues.foreach(value => gamefield += value + "\n")
    gamefield

  }
  
}
