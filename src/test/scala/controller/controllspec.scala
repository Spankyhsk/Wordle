package controller
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import model.attempt

class controllspec extends AnyWordSpec {
  "The Controller" should{
    val contoller = new controll("fisch", 1)
    "Controller should initialize versuch and targetword from parameter" in{
      val versuch = 1
      val targetword = "fisch"
      controller.versuch should be(1)
      controller.targetword should be("fisch")
    }
    
    "Count compares "
  }
}
