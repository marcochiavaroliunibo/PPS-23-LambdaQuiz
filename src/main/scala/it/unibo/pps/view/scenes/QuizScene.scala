package it.unibo.pps.view.scenes

import it.unibo.pps.controller.QuestionController
import it.unibo.pps.model.Question
import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.{AnswersSpace, GameTitle, MainMenu, QuestionSpace, UIComponent}
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color.{DeepSkyBlue, DodgerBlue}
import scalafx.scene.paint.{LinearGradient, Stops}

/**
 * Questa classe rappresenta la pagina di gioco, composta da due componenti
 * QuestionSpace: visualizzazione della domanda del turno
 * AnswerSpace: visualizzazione delle risposte e possibilità di selezionarne una per giocare
 */
class QuizScene extends Scene:

  val question: Question = QuestionController.prepareQuestion()
  
  root = new BorderPane {
    top = QuestionSpace.getComponent
    center = AnswersSpace.getComponent
    background = UIUtils.craftBackground(new LinearGradient(endX = 0, stops = Stops(DodgerBlue, DeepSkyBlue)))
  }
end QuizScene

object QuizScene extends UIComponent[Scene]:
  private val menuScene = new MenuScene
  override def getComponent: Scene = menuScene
end QuizScene
