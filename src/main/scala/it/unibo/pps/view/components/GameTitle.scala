package it.unibo.pps.view.components

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Node
import scalafx.scene.paint.Color.{DarkOrange, Orange}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, Text}

private class GameTitle extends Text("LambdaQuiz"):
  font = new Font("Verdana Bold", 75)
  fill = new LinearGradient(endX = 0, stops = Stops(Orange, DarkOrange))
  alignmentInParent = Pos.Center
  margin = Insets(40, 0, 0, 0)
end GameTitle

object GameTitle extends UIComponent[Text]:
  private val gameTitle = new GameTitle
  override def getComponent: Text = gameTitle
end GameTitle
