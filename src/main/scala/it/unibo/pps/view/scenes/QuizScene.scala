package it.unibo.pps.view.scenes

import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.{AnswersSpace, GameTitle, MainMenu, QuestionSpace, UIComponent}
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color.{DeepSkyBlue, DodgerBlue}
import scalafx.scene.paint.{LinearGradient, Stops}

class QuizScene extends Scene:
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
