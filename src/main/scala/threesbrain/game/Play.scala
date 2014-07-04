package threesbrain.game

import scala.annotation.tailrec

object Play {
    @tailrec
    def play(player: ThreesPlayer, gameState: ThreesGame = ThreesGame.newGame(), allowInvalidMove: Boolean = true): ThreesGame = {
        val isGameOver = !Move.values.map(gameState.move(_)).exists(_ != gameState)
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