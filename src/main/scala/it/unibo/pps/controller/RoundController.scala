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

  def setPlayer(newPlayer: User): Unit = player = newPlayer

  def createRound(round: Round): Unit = roundRepository.create(round)

  def playRound(answer: Int): Boolean = {
    // todo: devo effettuare il check sulla risposta e aggiorna a DB:
    val numberPlayer: Int = getNumberPlayer
    var correct: Int = 0
    if QuestionController.getQuestion.getCorrectAnswerNumber == 1 then
      correct = 1
    if answer == QuestionController.getQuestion.getCorrectAnswerNumber then
      updatePoints(numberPlayer, true)
      Alert(AlertType.Confirmation, "Risposta corretta!", ButtonType.Close)
        .showAndWait()
      true
    else
      updatePoints(numberPlayer, false)
      Alert(AlertType.Error, "Risposta errata!", ButtonType.Close)
        .showAndWait()
      false
  }

  private def getNumberPlayer: Int =
    if player.getUsername == GameController.gameOfLoggedUsers.get.getUser1.getUsername then 1
    else 2

  private def updatePoints(numberPlayer: Int, correct: Boolean): Unit =
    val point = if correct then 1 else 0
    round.setPoint(numberPlayer, point)
    roundRepository.update(round)
  
end RoundController
