package model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class attemptspec extends AnyWordSpec{
  "A attempt" should{
    "have a target, and a x attempt," in{
      val attempt = new attempt("fisch", 1)
      attempt.targetword should be("fisch")
      attempt.x should be(1)
    }
  }
}
