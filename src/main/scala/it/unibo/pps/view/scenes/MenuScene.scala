package it.unibo.pps.view.scenes

import it.unibo.pps.view.UIUtils
import it.unibo.pps.view.components.{GameTitle, MainMenu}
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane

/** Componente grafico che funge da schermata principale dell'applicazione. Ospita il titolo del gioco ed il menu
  * principale.
  */
class MenuScene extends Scene:
  root = new BorderPane {
    top = GameTitle()
    center = MainMenu()
    background = UIUtils.defaultBackground
  }
end MenuScene

/** Factory per le istanze di [[MenuScene]]. */
object MenuScene:
  /** Crea la schermata principale dell'applicazione.
    * @return
    *   una nuova istanza della classe [[MenuScene]] sotto forma di una [[Scene]]
    */
  def apply(): Scene = new MenuScene
end MenuScene
