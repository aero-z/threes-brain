package threesbrain.ai

import threesbrain.game.Play

object TestAi extends App {
    val end = Play.play(RandomPlayer)
    println(end)
}