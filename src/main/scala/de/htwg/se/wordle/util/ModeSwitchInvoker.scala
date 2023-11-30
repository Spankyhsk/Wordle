package de.htwg.se.wordle.util

import de.htwg.se.wordle.util.Command

class ModeSwitchInvoker {
  var command: Option[Command] = None

  def setCommand(cmd: Command): Unit = {
    command = Some(cmd)
  }

  def executeCommand(): Unit = {
    command.foreach(_.execute())
  }
}
