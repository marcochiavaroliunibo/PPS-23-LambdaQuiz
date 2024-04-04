package it.unibo.pps.view.components

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color.{DarkOrange, Orange}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, Text}

private class GameTitle extends Text("LambdaQuiz"):
  font = new Font("Verdana Bold", 75)
  fill = new LinearGradient(endX = 0, stops = Stops(Orange, DarkOrange))
  alignmentInParent = Pos.Center
  margin = Insets(20, 0, 0, 0)
end GameTitle

object GameTitle:
  def apply(): Text = new GameTitle
end GameTitle
