package wordle
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._



class wordlespec extends AnyWordSpec {

  "Wordle" should {
    

      val array1Buchstab: Array[String] = Array("A", "B", "C", "D")
      val loesungsword = "B"

    "have an array and a solution word" in {
      array1Buchstab.mkString(", ") should be ("A, B, C, D")  //.mkString(", ") --> "A, B, C, D"
      loesungsword should be("B")
    }

    }




}
