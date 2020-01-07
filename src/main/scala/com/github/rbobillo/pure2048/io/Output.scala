package com.github.rbobillo.pure2048.io

import java.awt.Color

import cats.effect.IO
import com.github.rbobillo.pure2048.grid.Grid
import com.github.rbobillo.pure2048.io.gui.GridPanel
import javax.swing.JFrame

object Output {

  def logError[T](msg: String): IO[Unit] =
    IO.apply(println("\u001b[91merror\u001b[0m: " + msg))

  def displayGuiGrid(frame: JFrame, grid: Grid): IO[Unit] =
    for {
      p <- IO.pure(new GridPanel(grid, frame))
      _ <- IO.apply(frame.setContentPane(p))
      _ <- IO.apply(frame.setFocusable(true))
      _ <- IO.apply(frame.setTitle(s"2048 - Score: ${grid.score}"))
      _ <- IO.apply(frame.validate())
    } yield ()

}