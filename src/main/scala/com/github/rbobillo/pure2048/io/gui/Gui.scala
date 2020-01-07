package com.github.rbobillo.pure2048.io.gui

import java.awt.Dimension

import cats.effect.IO
import com.github.rbobillo.pure2048.grid.Grid
import javax.swing.JFrame

object Gui {

  private def createFrameAndPanel(grid: Grid, width: Int, height: Int): IO[(JFrame, GridPanel)] =
    for {
      f <- IO.pure(new JFrame)
      d <- IO.pure(new Dimension(width, height))
      p <- IO.pure(new GridPanel(grid, f))
      _ <- IO.apply(p.setPreferredSize(d))
    } yield f -> p

  private def initFrame(frame: JFrame, panel: GridPanel): IO[JFrame] =
    for {
      _ <- IO.apply(frame.setDefaultCloseOperation(3)) // EXIT_ON_CLOSE
      _ <- IO.apply(frame.getContentPane.add(panel))
      _ <- IO.apply(frame.pack())
      _ <- IO.apply(frame.setVisible(true))
      _ <- IO.apply(frame.addKeyListener(panel))
    } yield frame

  def initGUI(grid: Grid, width: Int, height: Int): IO[(JFrame, GridPanel)] =
    for {
      fp <- createFrameAndPanel(grid, width, height)
      _ <- initFrame(frame = fp._1, panel = fp._2)
    } yield fp

}