package it.unibo.pps.view.components

import it.unibo.pps.controller.{QuestionController, RoundController}
import it.unibo.pps.model.Question
import scalafx.application.Platform
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType}
import scalafx.scene.layout.*
import scalafx.scene.paint.Color.{Black, Gold, Goldenrod}
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.text.Font

/**
 Questa classe rappresenta la pagina di gioco, in cui i giocatori rispondono alle domande del turno
 */
private class AnswersSpace extends FlowPane(Orientation.Vertical, 0, 10):
  import it.unibo.pps.view.UIUtils.*

  private def craftButton(displayName: String): Button = new Button {
    text = displayName
    font = new Font("Comic Sans MS", 24)
    prefWidth = 250
    prefHeight = 40
    val buttonGradient = new LinearGradient(endX = 0, stops = Stops(Gold, Goldenrod))
    background = craftBackground(buttonGradient, 4)
    border = new Border(new BorderStroke(Black, BorderStrokeStyle.Solid, new CornerRadii(8), new BorderWidths(4)))
  }

  val question: Question = QuestionController.getQuestion

  private val menuButtons = Seq(question.getAnswers.head, question.getAnswers(1), question.getAnswers(2), question.getAnswers(3)).map(craftButton)

  menuButtons.filter(_.text.value == question.getAnswers.head).head.onAction = _ => RoundController.playRound(1)
  menuButtons.filter(_.text.value == question.getAnswers(1)).head.onAction = _ => RoundController.playRound(2)
  menuButtons.filter(_.text.value == question.getAnswers(2)).head.onAction = _ => RoundController.playRound(3)
  menuButtons.filter(_.text.value == question.getAnswers(3)).head.onAction = _ => RoundController.playRound(4)

  alignment = Pos.Center
  children = menuButtons
end AnswersSpace

object AnswersSpace extends UIComponent[FlowPane]:
  private val answersSpace = new AnswersSpace
  override def getComponent: FlowPane = answersSpace
end AnswersSpace