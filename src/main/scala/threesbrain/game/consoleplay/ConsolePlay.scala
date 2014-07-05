package threesbrain.game.consoleplay

import threesbrain.game.core.Play

object ConsolePlay extends App {
    val end = Play.play(ConsolePlayer)
    println(end)
    println("Score: " + end.score)
}