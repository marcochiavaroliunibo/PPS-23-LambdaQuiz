package it.unibo.pps.view.components

import it.unibo.pps.controller.{QuestionController, RoundController}
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
    button.onAction = e => answerQuestion(index)
    button
  })

  /** Metodo che sfrutta il controller per effettuare la giocacata e passare alla prossima domanda. Nel caso in cui non
    * ci siano più domande, il metodo riporta il giocatore alla dashboard.
    * @param answerIndex
    *   indice della risposta selezionata
    */
  private def answerQuestion(answerIndex: Int): Unit =
    val roundHasNextQuestion = RoundController.playRound(answerIndex)
    if roundHasNextQuestion then changeScene(this.scene.get, QuizScene())
    else changeScene(this.scene.get, DashboardScene())

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
