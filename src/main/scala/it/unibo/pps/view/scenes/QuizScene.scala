package it.unibo.pps.view.scenes

import it.unibo.pps.controller.{GameController, QuestionController, RoundController}
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.AnswersSpace
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.scene.text.{Font, Text, TextAlignment}

/** Questa classe rappresenta la pagina di gioco, composta da due componenti QuestionSpace: visualizzazione della
  * domanda del turno AnswerSpace: visualizzazione delle risposte e possibilità di selezionarne una per giocare
  */
class QuizScene extends Scene:
  QuestionController.prepareQuestion()
  if QuestionController.counterQuestionRound == 0 then
    val category: String = GameController.gameOfLoggedUsers
      .map(_.categories(RoundController.round.map(_.numberRound + 1).getOrElse(0)).toString)
      .getOrElse("")
    UIUtils.showSimpleAlert(
      AlertType.Information,
      s"Gioca ${RoundController.player.get.username} per la categoria $category"
    )

  private val questionText: Text = new Text {
    font = new Font("Roboto", 24)
    text = QuestionController.getQuestion.map(_.text).getOrElse("")
    textAlignment = TextAlignment.Center
    margin = Insets(20, 0, 0, 0)
    wrappingWidth = 500
  }

  root = new BorderPane {
    top = UIUtils.getSceneTitle("Schermata di gioco")
    center = new VBox(10) {
      alignment = Pos.Center
      children = List(questionText, AnswersSpace())
    }
    background = UIUtils.defaultBackground
  }
end QuizScene

object QuizScene:
  def apply(): Scene = new QuizScene
end QuizScene
