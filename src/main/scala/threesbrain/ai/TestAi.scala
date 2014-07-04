package threesbrain.ai

import scala.math
import threesbrain.game.Play
import threesbrain.ai.neuralnetwork.NeuralNetworkPlayer

object TestAi extends App {
    def mean(xs: List[Int]) = xs.sum.toDouble / xs.length
    def stdDev(xs: List[Int]) = {
        val m = mean(xs)
        math.sqrt(xs.foldLeft(0.0)((x,y) => x + math.pow(m - y, 2)) / xs.length)
    }

    val nTests = 20
    val playersToTest = List(
        ("Random Moves", RandomPlayer),
        ("Random Neural Network", NeuralNetworkPlayer.makeRandom())
    )
    for ((name, player) <- playersToTest) {
        println("\nPlayer: " + name)
        val scores = List.fill(nTests)(Play.play(player, allowInvalidMove=false).score)
        println("Score mean: " + mean(scores))
        println("Score std dev: " + stdDev(scores))
    }
}