package it.unibo.pps.view.components

import it.unibo.pps.controller.{QuestionController, RoundController}
import it.unibo.pps.model.Question
import it.unibo.pps.view.scenes.{DashboardScene, QuizScene}
import scalafx.Includes.*
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.layout.*

/** Questa classe rappresenta la pagina di gioco, in cui i giocatori rispondono alle domande del turno
  */
private class AnswersSpace extends FlowPane(Orientation.Vertical, 0, 10):
  import it.unibo.pps.view.UIUtils.*

  private val question: Question = QuestionController.getQuestion
  private val menuButtons = question.getAnswers.zipWithIndex.map { case (answer, index) =>
    val button = craftButton(answer)
    button.onAction = _ => callResponse(index + 1)
    button
  }

  /** aggiorno il round in base la risposta dell'utente quando l'utente clicca avaanti, mostro la domanda successiva o
    * torno in dashboard
    */
  private def callResponse(answer: Int): Unit = {
    RoundController.playRound(answer)
    if QuestionController.nextQuestion then changeScene(scene.get(), new QuizScene)
    else changeScene(scene.get(), new DashboardScene)
  }

  alignment = Pos.Center
  children = menuButtons
end AnswersSpace

object AnswersSpace:
  def apply(): FlowPane = new AnswersSpace
end AnswersSpace
