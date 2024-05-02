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

/** Companion object che funge da factory per la classe [[Report]].
  */
object Report {

  /** Crea un oggetto di tipo [[Report]].
    * @param adversaryName
    *   Nome dell'utente oggetto della statistica
    * @param playerPoints
    *   Punti dell'utente in una partita
    * @param adversaryPoints
    *   Punti dell'avversario in una partita
    * @return
    *   Oggetto di tipo [[Report]]
    */
  def apply(adversaryName: String, playerPoints: Int, adversaryPoints: Int): Report =
    new Report(adversaryName, playerPoints, adversaryPoints)
}
