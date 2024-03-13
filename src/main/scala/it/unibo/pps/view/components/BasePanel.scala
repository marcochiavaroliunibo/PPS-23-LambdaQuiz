package it.unibo.pps.view.components

import scalafx.geometry.Insets
import scalafx.scene.Node
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, CornerRadii}
import scalafx.scene.paint.*
import scalafx.scene.paint.Color.{DeepSkyBlue, DodgerBlue}

/** This object represents the base panel of the application, which contains the main menu
  */
object BasePanel extends UIComponent:
  private class BasePanel extends BorderPane:
    top = GameTitle.getComponent
    center = MainMenu.getComponent
    background = new Background(
      Array(
        new BackgroundFill(
          new LinearGradient(endX = 0, stops = Stops(DodgerBlue, DeepSkyBlue)),
          CornerRadii.Empty,
          Insets.Empty
        )
      )
    )

  end BasePanel

  private val basePanel = new BasePanel
  override def getComponent: Node = basePanel
end BasePanel
