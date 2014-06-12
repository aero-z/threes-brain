package threesbrain

import scala.io.StdIn

object ConsolePlay extends App {
    val end = Play.play(ConsolePlayer)
    println(end)
}