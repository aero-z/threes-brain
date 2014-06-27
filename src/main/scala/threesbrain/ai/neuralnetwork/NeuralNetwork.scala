package threesbrain.ai.neuralnetwork

import scala.util.Random

/**
 * Neuron with assigned weights (first weight is for the bias)
 */
class Neuron(val weights: List[Double]) {
    def logisticFunc(x: Double) = 1.0 / (1.0 + Math.exp(-x))
        
    def evaluate(inputs: List[Double]): Double = {
        assert(weights.length == inputs.length + 1)
        logisticFunc((1.0 :: inputs).zip(weights).map({case (i, w) => i * w}).reduce(_ + _))
    }
}

class NeuronLayer(val neurons: List[Neuron]) {
    def evaluate(inputs: List[Double]) = neurons.map(_.evaluate(inputs))
}

/**
 * Layered feed forward network where every neuron is connected to all neurons in the previous layer
 */
class NeuralNetwork(val layers: List[NeuronLayer]) {
    def evaluate(inputs: List[Double]) = {
        layers.foldLeft(inputs)((outputs, layer) => layer.evaluate(outputs))
    }
}

object NeuralNetwork {
    def makeRandom(layerSizes: List[Int]) = {
        assert(layerSizes.length >= 2)
        val ioSizes = layerSizes.dropRight(1).zip(layerSizes.tail)
        val layers = ioSizes.map({case (nInputs, nOutputs) =>
            new NeuronLayer(List.fill(nOutputs)(new Neuron(List.fill(nInputs + 1)(Random.nextDouble()))))
        })
        new NeuralNetwork(layers)
    }
}
