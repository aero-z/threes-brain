package threesbrain

import scala.io.StdIn

object Play extends App {
    val prompt = "Enter your move (or q/quit): "
    val quitRegex = "^(quit|q)$".r
    val lineStream = Iterator.continually(StdIn.readLine(prompt))
                             .takeWhile(quitRegex.findFirstIn(_) == None)

    val newGame = ThreesGame.newGame
    println(newGame)
    lineStream.foldLeft(newGame)((game, line) => {
        val next = game.move(Move.withName(line))
        println(next)
        next
    })
}