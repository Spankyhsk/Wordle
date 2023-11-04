package de.htwg.se.wordle.model

import de.htwg.se.wordle.model.attempt
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class attemptspec extends AnyWordSpec with Matchers{
  "A attempt" should{
    "have a target, and a x attempt," in{
      val attempt = new attempt("fisch", 1)
      attempt.targetword should be("fisch")
      attempt.x should be(1)
    }
  }
}
