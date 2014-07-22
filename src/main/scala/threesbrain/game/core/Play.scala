package threesbrain.game.core

import scala.annotation.tailrec

object Play {
    @tailrec
    def play(player: ThreesPlayer, gameState: ThreesGame = ThreesGame.newGame(), allowInvalidMove: Boolean = true): ThreesGame = {
        val isGameOver = gameState.validMoves.isEmpty
        if (isGameOver) {
            gameState
        } else {
            val move = player.decideMove(gameState)
            val newState = gameState.move(move)
            if (!allowInvalidMove && newState == gameState)
                gameState
            else
                play(player, newState, allowInvalidMove)
        }
    }
}