package it.unibo.pps.controller

import it.unibo.pps.business.RoundRepository
import it.unibo.pps.model.{Game, Round, User}
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

  def getPlayer: User = player
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

  /** aggiorna il punteggio (dell'utente che ha risposto) per il round in corso */
  private def updatePoints(correct: Boolean): Unit =
    round.setPoint(player, correct)
    roundRepository.update(round, round.getID)

  /** Calcola il punteggio di un utente sulla base dei round che fino a quel momento ha giocato.
    * @param user
    *   Lo [[User]] di cui si vuole conoscere il punteggio
    *   Eventualmente, è possibile passare un [[Game]], altrimenti si prenderà quello che si sta giocando
    * @return
    *   il punteggio di [[user]], se ci sono round giocati. Altrimenti [[0]]
    */
  def computePartialPointsOfUser(user: User, game: Game = null): Int =
    val allRounds = game match
      case null => Await.result(roundRepository.getAllRoundsByGame(GameController.gameOfLoggedUsers.orNull), 5.seconds)
      case _ => Await.result(roundRepository.getAllRoundsByGame(game), 5.seconds)
    allRounds
      .getOrElse(List.empty)
      .flatMap(_.scores)                        // trasforma la lista di Round in lista di Score
      .filter(_.user.username == user.username) // filtra soo le Score dell'utente in input
      .filter(_.score != -1)                    // esclude i valori -1 (round non ancora giocato dall'utente)
      .foldRight(0)(_.score + _)                // calcola il punteggio per accumulazione
  
  def getPlayedRounds: List[Round] =
    GameController.gameOfLoggedUsers
      .flatMap(game =>
        Await
          .result(roundRepository.getAllRoundsByGame(game), 5.seconds)
      )
      .getOrElse(List.empty)
    
  def getAllRoundByGame(game: Game): List[Round] = {
    Await.result(roundRepository.getAllRoundsByGame(game), 5.seconds).getOrElse(List.empty)
  }

  /** a fine giocata, resetto le variabili per la prossima giocata */
  def resetVariable(): Unit =
    round = null
    player = null
  
end RoundController
