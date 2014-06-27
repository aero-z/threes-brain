package threesbrain.ai

import scala.math
import reflect.runtime.universe._
import threesbrain.game.Play
import threesbrain.ai.neuralnetwork.NeuralNetworkPlayer

object TestAi extends App {
    def mean(xs: List[Int]) = xs.sum.toDouble / xs.length
    def stdDev(xs: List[Int]) = {
        val m = mean(xs)
        math.sqrt(xs.foldLeft(0.0)((x,y) => x + math.pow(m - y, 2)) / xs.length)
    }

    //def className[T: TypeTag](x: T) = typeOf[T].typeSymbol.fullName
    def className[T](x: T) = x.getClass().getSimpleName()

    val nTests = 20
    val playersToTest = List(RandomPlayer, NeuralNetworkPlayer.makeRandom())
    for (player <- playersToTest) {
        println("\nPlayer: " + className(player))
        val scores = List.fill(nTests)(Play.play(player).score)
        println("Score mean: " + mean(scores))
        println("Score std dev: " + stdDev(scores))
    }
}