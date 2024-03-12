package it.unibo.pps.view.components

import scalafx.Includes.*
import scalafx.scene.Node
import scalafx.scene.control.Dialog

trait DialogComponent extends UIComponent {
  def getDialog: Dialog[?]

  override def getComponent: Node = getDialog.dialogPane()
}

