package threesbrain.game.ai

import scala.util.Random
import threesbrain.game.core._

object RandomPlayer extends ThreesPlayer {
    def decideMove(gameState: ThreesGame) = {
        Move.values.toList(Random.nextInt(Move.values.size))
    }
}