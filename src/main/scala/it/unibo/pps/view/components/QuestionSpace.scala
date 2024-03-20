package it.unibo.pps.view.components

import it.unibo.pps.controller.{GameController, QuestionController, RoundController}
import it.unibo.pps.model.{Category, Question, Round, User}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.paint.Color.{DarkOrange, Orange}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.{Font, Text}

/**
 * Questa classe rappresenta la pagina di gioco, in cui i giocatori visualizzano la domanda del turno
 */
private class QuestionSpace extends Text():
  font = new Font("Verdana Bold", 75)
  fill = new LinearGradient(endX = 0, stops = Stops(Orange, DarkOrange))
  alignmentInParent = Pos.Center
  margin = Insets(40, 0, 0, 0)
  text = QuestionController.getQuestion.getText
end QuestionSpace

object QuestionSpace extends UIComponent[Text]:
  // private val questionSpace = new QuestionSpace
  /** nel caso dei componenti della domanda ho bisogno di passare ogni volta una 
   * nuova istanza, in modo da poter cambiare sempre il quiz mostrato
  */
  override def getComponent: Text = new QuestionSpace
end QuestionSpace