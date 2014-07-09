package threesbrain.neuralnetwork

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

/**
 * A layer of fully connected neurons
 */
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
    def weightLengths(layerSizes: List[Int]) =
        layerSizes.dropRight(1).zip(layerSizes.tail).map({case (i, o) => (i + 1) * o})

    def makeRandom(layerSizes: List[Int]) = {
        val numWeights = weightLengths(layerSizes).sum
        val weights = List.fill(numWeights)(Random.nextDouble() * 2.0 - 1.0)
        fromWeights(layerSizes, weights)
    }

    private def split[T](list: List[T], sizes: List[Int]): List[List[T]] = sizes match {
        case Nil => Nil
        case _ => list.splitAt(sizes.head) match {
            case (a, b) => a :: split(b, sizes.tail)
        }
    }

    def fromWeights(layerSizes: List[Int], weights: List[Double]) = {
        assert(layerSizes.length >= 2)

        val layerWeights = split(weights, weightLengths(layerSizes))

        assert(layerWeights.length == layerSizes.length - 1)
        val layers = layerWeights.zip(layerSizes.dropRight(1)).map({case (weights, nInputs) =>
            new NeuronLayer(weights.grouped(nInputs + 1).map(new Neuron(_)).toList)
        })
        new NeuralNetwork(layers)
    }
}
