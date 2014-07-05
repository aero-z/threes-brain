package threesbrain.game.consoleplay

import scala.io.StdIn
import threesbrain.game.core._

object ConsolePlayer extends ThreesPlayer {
    val prompt = "Enter your move (l/r/u/d): "

    def decideMove(gameState: ThreesGame) = {
        println(gameState)
        Iterator.continually(StdIn.readLine(prompt)).collectFirst({
            case "l" => Move.Left
            case "r" => Move.Right
            case "u" => Move.Up
            case "d" => Move.Down
        }).get
    }
}