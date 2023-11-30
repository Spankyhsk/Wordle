package de.htwg.se.wordle.util


import de.htwg.se.wordle.controller
import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.model.gamemode

import scala.util.{Failure, Success, Try}

trait Command {
  def execute(): Unit
  //def undo(): Unit
}

// Concrete command class for changing state


case class EasyModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(1)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("leicht")
  }
}

case class MediumModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(2)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("mittel")
  }
}

case class HardModeCommand(controller: controll) extends Command {
  override def execute(): Unit = {
    controller.changeState(3)
    controller.createGameboard()
    controller.createwinningboard()
    //controller.setDifficulty("schwer")
  }
}

