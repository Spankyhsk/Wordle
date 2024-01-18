package de.htwg.se.wordle.model.FileIOComponent

import de.htwg.se.wordle.model.GameInterface
trait FileIOInterface {
  
  def load(game:GameInterface):String
  def save(game: GameInterface):Unit

}
