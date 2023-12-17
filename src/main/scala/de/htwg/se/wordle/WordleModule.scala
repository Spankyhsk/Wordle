package de.htwg.se.wordle

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.wordle.model.*
import de.htwg.se.wordle.controller.*
import de.htwg.se.wordle.model.gamefieldComponent.gameboard
import de.htwg.se.wordle.model.gamemechComponent.GameMech
import de.htwg.se.wordle.model.gamemodeComponnent.gamemode

class WordleModule extends AbstractModule with ScalaModule {
  override def configure():Unit= {
    bind[GameInterface].to[Game]
    bind[ControllerInterface].toInstance(controll(Game(new GameMech(), new gameboard() , gamemode(1))))
  }

}
