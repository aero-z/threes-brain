package threesbrain.game.ai

import threesbrain.game.core._
import threesbrain.neuralnetwork._
import Move._

object NeuralNetworkPlayer {
    val numInputs = 16 + 1 + 3 // cells + next card + stack
    val numOutputs = 1
    
    def makeRandom() = // creates a random neural network player with 2 layers
        new NeuralNetworkPlayer(NeuralNetwork.makeRandom(List(numInputs, numOutputs)))

    val numLayers = 3
    def scoreFun(nn: NeuralNetwork) = Play.play(new NeuralNetworkPlayer(nn), allowInvalidMove=false).score

    def train() =
        new NeuralNetworkPlayer(GeneticAlgorithm.train(scoreFun, List(numInputs, 16, numOutputs)))
}

class NeuralNetworkPlayer(neuralNetwork: NeuralNetwork) extends ThreesPlayer {
    assert(neuralNetwork.layers.head.neurons(0).weights.length - 1 == NeuralNetworkPlayer.numInputs)
    assert(neuralNetwork.layers.last.neurons.length == NeuralNetworkPlayer.numOutputs)
    
    def gameStateToNetworkInput(gameState: ThreesGame): List[Double] = {
        gameState.cells.flatten.map(_.toDouble) :::
        gameState.nextCard.toDouble ::
        gameState.cardsInStack.map(_.toDouble)
    }
    def networkOutputToMove(outputs: List[Double]): Move = {
        Move.values.toList(Math.min(0, (outputs(0) * 4).toInt))
    }
        
    def decideMove(gameState: ThreesGame): Move = {
        val inputs = gameStateToNetworkInput(gameState)
        val outputs = neuralNetwork.evaluate(inputs)
        networkOutputToMove(outputs)
    }
}
