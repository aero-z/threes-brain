package threesbrain

object ThreesGame {
    def newGame = {
        new ThreesGame(
            ((0 :: 1 :: 0 :: 1 :: Nil) ::
            (0 :: 0 :: 0 :: 2 :: Nil) ::
            (0 :: 1 :: 0 :: 0 :: Nil) ::
            (2 :: 1 :: 3 :: 4 :: Nil) :: Nil).transpose
        )
    }
}

object Move extends Enumeration {
    type Move = Value
    val Up, Down, Left, Right = Value
}
import Move._

class ThreesGame(val cells: List[List[Int]]) {

    def move(move: Move): ThreesGame = {
        def shiftLine(line: List[Int]): List[Int] = line match {
            case 0 :: cs => cs ::: List(0)
            case c1 :: c2 :: cs if (c1 + c2 == 3) => 3 :: cs ::: List(0)
            case c1 :: c2 :: cs if (c1 == c2 && c1 + c2 > 4) => c1 + 1 :: cs ::: List(0)
            case c1 :: c2 :: cs => c1 :: shiftLine(c2 :: cs)
            case _ => line
        }
        def moveUp(cells: List[List[Int]]) = cells.map(shiftLine)
        new ThreesGame(move match {
            case Left => moveUp(cells.transpose).transpose
            case Right => moveUp(cells.transpose.map(_.reverse)).map(_.reverse).transpose
            case Up => moveUp(cells)
            case Down => moveUp(cells.map(_.reverse).map(_.reverse))
        })
    }

    override lazy val toString = {
        cells.transpose.map(_.map(_ match {
            case 0 => "."
            case n if (n <= 3) => n
            case n => (3 * (1 << (n-3))).toString
        }).mkString("\t")).mkString("\n")
    }
}

