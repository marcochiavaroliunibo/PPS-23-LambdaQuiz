package it.unibo.pps.model

/** Rappresenta una statistica relativa ad un utente in una partita.
  * @param adversaryName
  *   Nome dell'avversario in quella partita
  * @param playerPoints
  *   Punti dell'utente in quella partita
  * @param adversaryPoints
  *   Punti dell'avversario in quella partita
  */
case class Report(adversaryName: String, playerPoints: Int, adversaryPoints: Int)
