package it.unibo.pps.controller

import it.unibo.pps.business.RoundRepository
import it.unibo.pps.model.{Round, User}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}

import scala.concurrent.Await
import scala.concurrent.duration.*

object RoundController:

  private var round: Round = null
  private var player: User = null
  private val roundRepository = new RoundRepository

  def setRound(newRound: Round): Unit = round = newRound

  def getRound: Round = round

  def setPlayer(newPlayer: User): Unit = player = newPlayer

  def createRound(round: Round): Unit = roundRepository.create(round)

  /** controlla se la risposta è corretta: mostra il risutato all'utente e poi aggiorna i punti */
  def playRound(answer: Int): Boolean = {
    if answer == QuestionController.getQuestion.correctAnswer then
      updatePoints(true)
      Alert(AlertType.Confirmation, "Risposta corretta!", ButtonType.Close)
        .showAndWait()
      true
    else
      updatePoints(false)
      Alert(AlertType.Error, "Risposta errata!", ButtonType.Close)
        .showAndWait()
      false
  }

  /** ottiene se sta giocando utente 1 o utente 2 */
  /*private def getNumberPlayer: Int =
    if player.username == GameController.gameOfLoggedUsers.get.user1.username then 1
    else 2
   */

  /** aggiorna il punteggio (dell'utente che ha risposto) per il round in corso */
  private def updatePoints(correct: Boolean): Unit =
    round.setPoint(player, correct)
    roundRepository.update(round, round.getID)

  /** Calcola il punteggio di un utente sulla base dei round che fino a quel momento ha giocato.
    * @param user
    *   Lo [[User]] di cui si vuole conoscere il punteggio
    * @return
    *   il punteggio di [[user]], se ci sono round giocati. Altrimenti [[0]]
    */
  def computePartialPointsOfUser(user: User): Int =
    val allRounds = Await.result(roundRepository.getAllRoundsByGame(GameController.gameOfLoggedUsers.orNull), 5.seconds)
    allRounds
      .getOrElse(List.empty)
      .flatMap(_.scores)                        // trasforma la lista di Round in lista di Score
      .filter(_.user.username == user.username) // filtra soo le Score dell'utente in input
      .foldRight(0)(_.score + _)                // calcola il punteggio per accumulazione

  def getPlayedRounds: List[Round] =
    GameController.gameOfLoggedUsers
      .flatMap(game =>
        Await
          .result(roundRepository.getAllRoundsByGame(game), 5.seconds)
      )
      .getOrElse(List.empty)

end RoundController
