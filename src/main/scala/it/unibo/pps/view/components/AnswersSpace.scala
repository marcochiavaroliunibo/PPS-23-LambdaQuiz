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

  /** Lista dei 4 pulsanti utilizzati per mostrare e selezionare le risposte alle domande */
  private val answersButtons = QuestionController.getQuestion.map(_.answers.zipWithIndex.map { case (answer, index) =>
    val btnColors = getAnswerBtnColor(index)
    val button = craftButton(answer, btnColors)
    button.onAction = e => answerQuestion(index + 1)
    button
  })

  /** Gestore dell'evento [[scalafx.scene.control.ButtonBase.onAction]] per i bottoni relativi alle risposte della
    * domanda corrente.
    *
    * Scatena la procedura di gioco del round corrente, la quale gestisce anche il cambio della domanda a cui rispondere
    * ed il ritorno alla dashboard.
    *
    * @param answerIndex
    *   il numero della risposta selezionata dall'utente in fase di gioco
    */
  private def answerQuestion(answerIndex: Int): Unit = {
    RoundController.playRound(answerIndex)
    // TODO: spostare questa logica in Round Controller
    if QuestionController.nextQuestion then changeScene(scene.get(), QuizScene())
    else
      GameController.checkFinishGame()
      RoundController.resetVariable()
      changeScene(scene.get(), DashboardScene())
  }

  alignment = Pos.Center
  children = answersButtons.getOrElse(List.empty)
end AnswersSpace

/** Factory per le istanze di [[AnswersSpace]]. */
object AnswersSpace:
  /** Crea il componente che visualizza la domanda e le relative risposte.
    * @return
    *   una nuova istanza della classe [[AnswersSpace]] sotto forma di un [[FlowPane]]
    */
  def apply(): FlowPane = new AnswersSpace
end AnswersSpace
