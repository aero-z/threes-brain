package threesbrain.game.core

import Move._

trait ThreesPlayer {
    def decideMove(gameState: ThreesGame): Move
}