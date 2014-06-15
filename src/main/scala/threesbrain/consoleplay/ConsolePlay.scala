package threesbrain.consoleplay

import threesbrain.game.Play

object ConsolePlay extends App {
    val end = Play.play(ConsolePlayer)
    println(end)
    println("Score: " + end.score)
}