package de.htwg.se.wordle

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.wordle.controller.*


class WordleModule extends AbstractModule with ScalaModule {
  override def configure():Unit= {
    bind[ControllerInterface].toInstance(controll("norm"))
  }

}
