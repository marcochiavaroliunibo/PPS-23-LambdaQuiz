package it.unibo.pps.view.components

import it.unibo.pps.controller.{GameController, QuestionController, RoundController}
import it.unibo.pps.model.{Category, Question, Round, User}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color.{DarkOrange, Orange}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, Text}

private class QuestionSpace extends Text():
  
  val question: Question = QuestionController.prepareQuestion()

  font = new Font("Verdana Bold", 75)
  fill = new LinearGradient(endX = 0, stops = Stops(Orange, DarkOrange))
  alignmentInParent = Pos.Center
  margin = Insets(40, 0, 0, 0)
  text = question.getText
end QuestionSpace

/*
Per recuperare la domanda da fare:
1) recuperare l'ultimo round
2a) se non c'è, si inizia da [round 1 - user 1]
2b) se c'è, vedere se è in corso o completato
  3a) se è in corso, [round x - user 2]
  3b) se è completato, [round x + 1 - user 1]
 */

object QuestionSpace extends UIComponent[Text]:
  private val questionSpace = new QuestionSpace
  override def getComponent: Text = questionSpace
end QuestionSpace