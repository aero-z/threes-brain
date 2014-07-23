package threesbrain.game.ai

import scala.math
import threesbrain.game.core._

object TestAi extends App {
    def mean(xs: List[Int]) = xs.sum.toDouble / xs.length
    def stdDev(xs: List[Int]) = {
        val m = mean(xs)
        math.sqrt(xs.foldLeft(0.0)((x,y) => x + math.pow(m - y, 2)) / xs.length)
    }

    val nTests = 20
    val trainedNNPlayer = NeuralNetworkPlayer.train()

    val playersToTest: List[(String, () => ThreesPlayer)] = List(
        ("Random Moves", () => RandomPlayer),
        ("Random Neural Network", () => NeuralNetworkPlayer.makeRandom),
        ("Genetically Trained Neural Network", () => trainedNNPlayer)
    )
    for ((name, getPlayer) <- playersToTest) {
        println("Player: " + name)
        val scores = List.fill(nTests)(Play.play(getPlayer(), allowInvalidMove=false).score)
        println("Score max: " + scores.max)
        println("Score min: " + scores.min)
        println("Score mean: " + mean(scores))
        println("Score std dev: " + stdDev(scores))
        println()
    }
}
