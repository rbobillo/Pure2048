package com.github.rbobillo.pure2048

import akka.actor.{ActorRef, ActorSystem, Props}
import cats.effect.{ExitCode, IO, IOApp}
import com.github.rbobillo.pure2048.actors.{GameBoardActor, GridActor}
import com.github.rbobillo.pure2048.grid.Grid
import com.github.rbobillo.pure2048.grid.Merging.Tiles
import com.github.rbobillo.pure2048.io.Output
import com.github.rbobillo.pure2048.io.gui.Gui

object Main extends IOApp {

  private def initActors: IO[(ActorRef, ActorRef)] =
    for {
      sys2048 <- IO.apply(ActorSystem("2048ActorSystem"))
      grActor <- IO.apply(sys2048.actorOf(Props[GridActor], "GridActor"))
      gbActor <- IO.apply(sys2048.actorOf(Props[GameBoardActor], "GameBoardActor"))
    } yield grActor -> gbActor

  private def initGrid(initialTiles: Tiles): IO[Grid] =
    for {
      g0 <- IO.pure(Grid(initialTiles))
      g1 <- IO.apply(g0.addTile())
      g2 <- IO.apply(g1.addTile())
    } yield g2

  def run(args: List[String]): IO[ExitCode] =
    for {
      g <- initGrid(initialTiles = Array.fill(4)(Array.fill(4)(0)))
      f <- Gui.initGUI(g, 540, 540)
      _ <- Output.displayGuiGrid(grid  = g, panel = f._2, frame = f._1)
    } yield ExitCode.Success

}
