package threesbrain.ai.neuralnetwork

import threesbrain.game._
import Move._

class NeuralNetworkPlayer(neuralNetwork: NeuralNetwork) extends ThreesPlayer {
    val numInputs = 13
    val numOutputs = 1
    assert(neuralNetwork.layers.head.neurons.length == numInputs)
    assert(neuralNetwork.layers.last.neurons.length == numOutputs)
    
    def gameStateToNeuronInputs(gameState: ThreesGame): List[Double] =
        ???
    def neuronOutputsToMove(outputs: List[Double]): Move =
        ???
    def decideMove(gameState: ThreesGame): Move = {
        val inputs = gameStateToNeuronInputs(gameState)
        val outputs = neuralNetwork.evaluate(inputs)
        neuronOutputsToMove(outputs)
    }
}