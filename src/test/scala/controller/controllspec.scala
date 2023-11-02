package controller
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import model.attempt


class controllspec extends AnyWordSpec {
  "The Controller" should{
    val contoller = controll(new attempt("fisch", 1))
    "Controller should initialize versuch and targetword from parameter" in{
      val limit = controller.x
      val targetword = attempt.targetword
      limit should be(1)
      targetword should be("fisch")
    }

    "Count compares. number of attempt smaller as limit" in{
      val controller  = new controll(new attempt("fisch", 2))
      controller.count(1) should be(true)
    }

    "Count compares. number of attampt even as limit" in{
      val controller = new controll(new attempt("fisch", 2))
      count(2) should be(false)
    }
  }
}
