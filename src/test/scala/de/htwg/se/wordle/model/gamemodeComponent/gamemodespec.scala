/*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GamemodeSpec extends AnyWordSpec with Matchers {

  "A Gamemode" when {
    "in gamemode1" should {
      val gamemode = gamemode(1)

      "have a target word map with one word" in {
        gamemode.getTargetword().size should be(1)
      }

      "have a limit of 3" in {
        gamemode.getLimit() should be(3)
      }

      "have a word list with words of length 2" in {
        gamemode.getWordList().forall(_.length == 2) should be(true)
      }

      "have a string representation" in {
        gamemode.toString() should include("Wort 1:")
      }
    }

    "in gamemode2" should {
      val gamemode = gamemode(2)

      "have a target word map with two words" in {
        gamemode.getTargetword().size should be(2)
      }

      "have a limit of 4" in {
        gamemode.getLimit() should be(4)
      }

      "have a word list with words of length 2" in {
        gamemode.getWordList().forall(_.length == 2) should be(true)
      }

      "have a string representation" in {
        gamemode.toString() should include("Wort 1:")
        gamemode.toString() should include("Wort 2:")
      }
    }

    "in gamemode3" should {
      val gamemode = gamemode(3)

      "have a target word map with four words" in {
        gamemode.getTargetword().size should be(4)
      }

      "have a limit of 6" in {
        gamemode.getLimit() should be(6)
      }

      "have a word list with words of length 2" in {
        gamemode.getWordList().forall(_.length == 2) should be(true)
      }

      "have a string representation" in {
        gamemode.toString() should include("Wort 1:")
        gamemode.toString() should include("Wort 2:")
        gamemode.toString() should include("Wort 3:")
        gamemode.toString() should include("Wort 4:")
      }
    }
  }
}
*/

