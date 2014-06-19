threes-brain
============

AI for the mobile game [Threes!](http://asherv.com/threes/) using neural networks.

## neural network ##

* Every game state is mapped into _n_ input values in the range [-1, 1]
* The output of the network is mapped from [-1, 1] to one of the possible moves (up, down, left, right)
* Simple feedforward network
* Every neuron has a vector of weights in the range [-1, 1]; one weight is for the bias
* Propagation function is a [sigmoid](http://en.wikipedia.org/wiki/Sigmoid_function)
* Network is trained using a genetic algorithm ([ai-junkie.com](http://www.ai-junkie.com/ga/intro/gat1.html) has a good introductory article)

## statistics ##

TODO: add performance statistics of this AI
