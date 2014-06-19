package threesbrain.ai.neuralnetwork

class Neuron(val weights: List[Double]) {
    def evaluate(inputs: List[Double]) = {
        assert(weights.length == inputs.length + 1)
        (1.0 :: inputs).zip(weights).map({case (i, w) => i * w}).reduce(_ + _)
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
