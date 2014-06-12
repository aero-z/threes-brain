package threesbrain.game

import Move._

trait ThreesPlayer {
    def decideMove(gameState: ThreesGame): Move
}