package it.unibo.pps.view

import scalafx.geometry.Insets
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii}
import scalafx.scene.paint.Paint

object UIUtils:

  def craftBackground(paint: Paint): Background = 
    new Background(Array(new BackgroundFill(paint, CornerRadii.Empty, Insets(4))))

end UIUtils

