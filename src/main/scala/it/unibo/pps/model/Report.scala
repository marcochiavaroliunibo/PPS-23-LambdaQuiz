package it.unibo.pps.model

case class Report(playerName: String, playerPoints: Int, adversaryPoints: Int)

object Report {
  def apply(playerName: String, playerPoints: Int, adversaryPoints: Int): Report =
    new Report(playerName, playerPoints, adversaryPoints)
}
