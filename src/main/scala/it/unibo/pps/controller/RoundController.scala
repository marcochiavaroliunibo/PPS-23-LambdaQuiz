package it.unibo.pps.controller

import it.unibo.pps.business.RoundRepository
import it.unibo.pps.model.{Round, User}
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.control.Alert.AlertType

import scala.concurrent.Await
import scala.concurrent.duration.Duration

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
  
end RoundController
