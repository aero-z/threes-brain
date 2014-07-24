threes-brain
============

Experimental AI for the mobile game [Threes!](http://asherv.com/threes/) using neural networks and a genetic algorithm.

## Neural network ##

* Simple feedforward network.
* The current game board, the next card and the set of remainding cards in the stack (see [Threes! card draw algorithm](http://forums.toucharcade.com/showthread.php?t=218248&page=53)) are encoded as real numbers and fed as input to the neural network.
* The output of the network is four real values in the range [0, 1] corresponding to how valuable each move would be (up, down, left, right). The move that has the best value and is a valid move will be selected.
* Propagation function is a [sigmoid](http://en.wikipedia.org/wiki/Sigmoid_function)
* Network is trained using a genetic algorithm ([ai-junkie.com](http://www.ai-junkie.com/ga/intro/gat1.html) has a good introductory article)

## Statistics ##

Performance statistics comming soon.
