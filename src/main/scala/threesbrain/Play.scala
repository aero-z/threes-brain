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
        val move = line match {
            case "l" => Move.Left
            case "r" => Move.Right
            case "u" => Move.Up
            case "d" => Move.Down
        }
        val next = game.move(move)
        next match {
            case Some(next) =>
                println(next)
                next
            case None =>
                println("Move not possible")
                game
        }
    })
}