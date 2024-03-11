package it.unibo.pps.view.components

import scalafx.geometry.Pos
import scalafx.scene.paint.Color.{DarkOrange, Orange}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, Text}

object GameTitle extends Text("LambdaQuiz"):
  font = new Font("Courier", 60)
  fill = new LinearGradient(endX = 0, stops = Stops(Orange, DarkOrange))
  alignmentInParent = Pos.Center
end GameTitle

