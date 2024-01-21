package de.htwg.se.wordle.controller
import de.htwg.se.wordle.util.Command
class SetCommand( key:Int, feedback: Map[Int, String], controller:controll)extends Command{

  override def doStep: Unit = {controller.gameboard.setR(1, key, feedback)}

  override def undoStep: Unit = {controller.gameboard.setR(1, key, blankfeedback(feedback))}

  def blankfeedback(feedback:Map[Int, String]):Map[Int,String]={
    val updatefeedback: Map[Int,String] = feedback.map{ case(key, _) => key -> "-"*controller.getTargetword()(1).length}
    updatefeedback
  }

}
