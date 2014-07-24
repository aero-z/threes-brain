threes-brain
============

Experimental AI for the mobile game [Threes!](http://asherv.com/threes/) using neural networks and a genetic algorithm.

## Neural network ##

* The nework structure is a simple feedforward network.
* The current game board, the next card and the set of remaining cards in the stack (see [Threes! card draw algorithm](http://forums.toucharcade.com/showthread.php?t=218248&page=53)) are encoded as 20 real numbers and are fed as input to the neural network.
* The output of the network is four real values in the range [0, 1] corresponding to how valuable the neural network thinks each move is (up, down, left, right). The valid move that has the best value will be selected.
* The propagation function is a [sigmoid](http://en.wikipedia.org/wiki/Sigmoid_function)
* The network is trained using a genetic algorithm ([ai-junkie.com](http://www.ai-junkie.com/ga/intro/gat1.html) has a good introductory article)

## Statistics ##

Performance statistics comming soon.
