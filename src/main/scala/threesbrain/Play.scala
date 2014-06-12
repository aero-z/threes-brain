package threesbrain

import scala.io.StdIn
import scala.annotation.tailrec

object Play {
    @tailrec
    def play(player: ThreesPlayer, gameState: ThreesGame = ThreesGame.newGame()): ThreesGame = {
        val isGameOver = !Move.values.map(gameState.move(_)).exists(_ != gameState)
        if (isGameOver) {
            gameState
        } else {
            val move = player.decideMove(gameState)
            play(player, gameState.move(move))
        }
    }
}