package de.htwg.se.wordle.model
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class gamefieldspec extends AnyWordSpec {
  "A gamefield" should {

    "buildGamefield filling the map with n keys and values" in {
      val gamefield = new gamefield()
      gamefield.buildGamefield(1, 1, "_")
      gamefield.map.size should be(1)
      gamefield.map.head._1 should be(1)
      gamefield.map(1) should be("_")

    }

    "buildGamefield continue loop when key smaller as n" in{
      val gamefield = new gamefield()
      gamefield.buildGamefield(2,1,"_")
      gamefield.map.size should be(2)
    }

    "set setting a new value in map for key" in{
      val gamefield = new gamefield()
      gamefield.buildGamefield(1,1,"_")
      gamefield.set(1, "a")
      gamefield.map(1) should be("a")
    }

    "toString makes a string from all values from map" in{
      val gamefield = new gamefield()
      gamefield.buildGamefield(1,1,"_")
      val s = gamefield.toString
      s should be("_\n")
    }
  }

}
