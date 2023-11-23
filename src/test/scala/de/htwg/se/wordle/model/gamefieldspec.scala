package de.htwg.se.wordle.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class gamefieldspec extends AnyWordSpec with Matchers {

  "Gamefield" when {
    "using gamefield" should {
      "set feedback and build gamefield correctly" in {
        val gamefieldComponent = gamefield.gamefield()
        gamefieldComponent.set(1, "feedback1")
        gamefieldComponent.set(2, "feedback2")
        gamefieldComponent.buildGamefield(3, 3, "value")

        gamefieldComponent.toString shouldBe "feedback1\nfeedback2\nvalue\n"
      }


    }

    "using gameboard" should {
      "build gameboard correctly" in {
        val gameboardComponent = gamefield.gameboard()
        gameboardComponent.buildGameboard(3, 1)

        gameboardComponent.toString shouldBe "\n\n\n"
      }

      "get children from gameboard" in {
        val gameboardComponent = gamefield.gameboard()
        gameboardComponent.buildGameboard(3, 1)

        val children = gameboardComponent.getChilderen(1)
        children shouldBe a[gamefield.gamefield]
      }


    }
  }
}
