package threesbrain.ai.neuralnetwork

import threesbrain.game._
import Move._

object NeuralNetworkPlayer {
    val numInputs = 16 + 1 + 12 // cells + next card + stack
    val numOutputs = 1
    
    def makeRandom() = 
        new NeuralNetworkPlayer(NeuralNetwork.makeRandom(List(numInputs, numOutputs)))
}

class NeuralNetworkPlayer(neuralNetwork: NeuralNetwork) extends ThreesPlayer {
    assert(neuralNetwork.layers.head.neurons(0).weights.length - 1 == NeuralNetworkPlayer.numInputs)
    assert(neuralNetwork.layers.last.neurons.length == NeuralNetworkPlayer.numOutputs)
    
    def gameStateToNetworkInput(gameState: ThreesGame): List[Double] = {
        def cardActivation(x: Int) = x.toDouble
        
        gameState.cells.flatten.map(cardActivation) :::
        cardActivation(gameState.nextCard) ::
        gameState.stack.padTo(12, 0).map(cardActivation)
    }
    def networkOutputToMove(outputs: List[Double]): Move = {
        Move.values.toList((outputs(0) * 4).toInt)
    }
        
    def decideMove(gameState: ThreesGame): Move = {
        val inputs = gameStateToNetworkInput(gameState)
        val outputs = neuralNetwork.evaluate(inputs)
        networkOutputToMove(outputs)
    }
}