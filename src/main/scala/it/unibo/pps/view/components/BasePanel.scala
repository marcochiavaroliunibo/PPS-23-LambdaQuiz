package it.unibo.pps.view.components

import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, CornerRadii}
import scalafx.scene.paint.{Color, CycleMethod, LinearGradient, RadialGradient, Stops}
import scalafx.scene.paint.Color.{DeepSkyBlue, DodgerBlue}

object BasePanel extends BorderPane:
  top = GameTitle
  center = MainMenu
  background = new Background(Array(new BackgroundFill(
    new LinearGradient(endX = 0, stops = Stops(DodgerBlue, DeepSkyBlue)),
    CornerRadii.Empty, Insets.Empty)))

end BasePanel

