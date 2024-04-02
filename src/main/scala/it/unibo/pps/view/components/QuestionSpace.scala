package it.unibo.pps.view.components

import it.unibo.pps.controller.QuestionController
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color.{DarkOrange, Orange}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, Text}

/** Questa classe rappresenta la pagina di gioco, in cui i giocatori visualizzano la domanda del turno
  */
private class QuestionSpace extends Text:
  font = new Font("Verdana Bold", 22)
  fill = new LinearGradient(endX = 0, stops = Stops(Orange, DarkOrange))
  alignmentInParent = Pos.Center
  margin = Insets(40, 0, 0, 0)
  text = QuestionController.getQuestion.map(_.text).getOrElse("")
end QuestionSpace

object QuestionSpace:
  def apply(): Text = new QuestionSpace
end QuestionSpace
