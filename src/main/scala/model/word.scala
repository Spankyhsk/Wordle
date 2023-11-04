package model

case class word(){

      val wordsByLength = Map(
            1 -> Array("A", "B", "C", "D", "E"),
            2 -> Array("AB", "BC", "CD", "DE", "EF"),
            3 -> Array("ABC", "BCD", "CDE", "DEF", "EFG"),
            4 -> Array("ABCD", "BCDE", "CDEF", "DEFG", "EFGH"),
            5 -> Array("ABCDE", "BCDEF", "CDEFG", "DEFGH", "EFGHI")
      )


}
