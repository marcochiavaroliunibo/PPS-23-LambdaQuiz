package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, QuestionController, RoundController}
import it.unibo.pps.model.{Category, Question}
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.{AnswersSpace, QuestionSpace}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.BorderPane

/** Questa classe rappresenta la pagina di gioco, composta da due componenti QuestionSpace: visualizzazione della
  * domanda del turno AnswerSpace: visualizzazione delle risposte e possibilità di selezionarne una per giocare
  */
class QuizScene extends Scene:

  val question: Question = QuestionController.prepareQuestion()
  if QuestionController.counterQuestionRound == 0 then
    val category: Category = GameController.gameOfLoggedUsers.get
      .categories(RoundController.getRound.numberRound - 1)
    UIUtils.showSimpleAlert(AlertType.Information,
      s"Gioca ${RoundController.getPlayer.username} per la categoria $category")

  root = new BorderPane {
    top = QuestionSpace()
    center = AnswersSpace()
    background = UIUtils.defaultBackground
  }
end QuizScene

object QuizScene:
  def apply(): Scene = new QuizScene
end QuizScene
