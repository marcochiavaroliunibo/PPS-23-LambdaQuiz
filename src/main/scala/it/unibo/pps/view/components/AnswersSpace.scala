package it.unibo.pps.view.components

import it.unibo.pps.controller.{QuestionController, RoundController}
import it.unibo.pps.model.Question
import it.unibo.pps.view.scenes.{DashboardScene, QuizScene}
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.control.Button
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.{Black, Gold, Goldenrod}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Font
import scalafx.Includes.*

/**
 Questa classe rappresenta la pagina di gioco, in cui i giocatori rispondono alle domande del turno
 */
private class AnswersSpace extends FlowPane(Orientation.Vertical, 0, 10):
  import it.unibo.pps.view.UIUtils.*

  private def craftButton(displayName: String): Button = new Button {
    text = displayName
    font = new Font("Comic Sans MS", 14)
    prefWidth = 250
    prefHeight = 40
    val buttonGradient = new LinearGradient(endX = 0, stops = Stops(Gold, Goldenrod))
    background = craftBackground(buttonGradient, 4)
    border = new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, new CornerRadii(8), new BorderWidths(4)))
  }

  private val question: Question = QuestionController.getQuestion
  private val menuButtons = Seq(question.getAnswers.head, question.getAnswers(1), question.getAnswers(2), question.getAnswers(3))
    .map(craftButton)
  menuButtons.filter(_.text.value == question.getAnswers.head).head.onAction = _ => callResponse(1)
  menuButtons.filter(_.text.value == question.getAnswers(1)).head.onAction = _ => callResponse(2)
  menuButtons.filter(_.text.value == question.getAnswers(2)).head.onAction = _ => callResponse(3)
  menuButtons.filter(_.text.value == question.getAnswers(3)).head.onAction = _ => callResponse(4)


  /** aggiorno il round in base la risposta dell'utente
   * quando l'utente clicca avaanti, mostro la domanda successiva o torno in dashboard */
  private def callResponse(answer: Int): Unit = {
    RoundController.playRound(answer)
    if (QuestionController.nextQuestion) then
      changeScene(scene.get(), new QuizScene)
    else
      changeScene(scene.get(), new DashboardScene)
  }

  alignment = Pos.Center
  children = menuButtons
end AnswersSpace

object AnswersSpace extends UIComponent[FlowPane]:
  //private val answersSpace = new AnswersSpace
  /** nel caso dei componenti della domanda ho bisogno di passare ogni volta una 
   * nuova istanza, in modo da poter cambiare sempre il quiz mostrato
   */
  override def getComponent: FlowPane = new AnswersSpace
end AnswersSpace