package it.unibo.pps.view.components

import it.unibo.pps.controller.{GameController, QuestionController, RoundController}
import it.unibo.pps.view.UIUtils.*
import it.unibo.pps.view.scenes.{DashboardScene, QuizScene}
import scalafx.Includes.*
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.layout.*

/** Componente grafico per la visualizzazione della schermata di gioco. Esso è composto dal testo della domanda a cui
  * rispondere e dai 4 bottoni relativi alle risposte.
  */
private class AnswersSpace extends FlowPane(Orientation.Vertical, 0, 10):

  private val answersButtons = QuestionController.getQuestion.map(_.answers.zipWithIndex.map { case (answer, index) =>
    val btnColor = getAnswerBtnColor(index)
    val button = craftButton(answer, colorTop = btnColor._1, colorDown = btnColor._2)
    button.onAction = _ => answer(index + 1)
    button
  })

  // TODO: spostare questa logica in Round Controller
  /** Metodo invocato ogni volta che viene premuto uno dei bottoni relativi alle risposte alla domanda corrente. La sua
    * invocazione ha l'effetto di giocare il round corrente e di mostrare la prossima domanda, se ce n'é una, oppure di
    * tornare alla dashboard.
    */
  private def answer(answerIndex: Int): Unit = {
    RoundController.playRound(answerIndex)
    if QuestionController.nextQuestion then changeScene(scene.get(), QuizScene())
    else
      GameController.checkFinishGame()
      RoundController.resetVariable()
      changeScene(scene.get(), DashboardScene())
  }

  alignment = Pos.Center
  children = answersButtons.getOrElse(List.empty)
end AnswersSpace

object AnswersSpace:
  def apply(): FlowPane = new AnswersSpace
end AnswersSpace
