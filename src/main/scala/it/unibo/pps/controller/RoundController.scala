package it.unibo.pps.controller

import it.unibo.pps.business.RoundRepository
import it.unibo.pps.model.{Game, Question, Round, User}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}
import scala.concurrent.Await
import scala.concurrent.duration.*

object RoundController:

  private var _round: Option[Round] = None
  private var _player: Option[User] = None
  private val roundRepository = new RoundRepository

  def round_=(newRound: Round): Unit = _round = Some(newRound)
  def round: Option[Round] = _round
  def player_=(newPlayer: User): Unit = _player = Some(newPlayer)
  def player: Option[User] = _player

  /** crea un round */
  def createRound(round: Round): Unit = roundRepository.create(round)

  /** controlla se la risposta è corretta: mostra il risutato all'utente e poi aggiorna i punti */
  def playRound(answer: Int): Boolean =
    QuestionController.getQuestion match
      case Some(q: Question) if q.correctAnswer == answer =>
        updatePoints(true)
        Alert(AlertType.Confirmation, "Risposta corretta!", ButtonType.Close)
          .showAndWait()
        true
      case Some(q: Question) =>
        updatePoints(false)
        Alert(AlertType.Error, "Risposta errata!", ButtonType.Close)
          .showAndWait()
        false
      case None => false

  /** aggiorna il punteggio (dell'utente che ha risposto) per il round in corso */
  private def updatePoints(correct: Boolean): Unit =
    player.flatMap(p =>
      round.map(r => {
        r.setPoint(p, correct)
        roundRepository.update(r, r.getID)
      })
    )

  /** Calcola il punteggio di un utente sulla base dei round che fino a quel momento ha giocato.
    * @param user
    *   Lo [[User]] di cui si vuole conoscere il punteggio Eventualmente, è possibile passare un [[Game]], altrimenti si
    *   prenderà quello che si sta giocando
    * @return
    *   il punteggio di [[user]], se ci sono round giocati. Altrimenti [[0]]
    */
  def computePartialPointsOfUser(user: User, game: Game = null): Int =
    val allRounds = game match
      case null => Await.result(roundRepository.getAllRoundsByGame(GameController.gameOfLoggedUsers.orNull), 5.seconds)
      case _ => Await.result(roundRepository.getAllRoundsByGame(game), 5.seconds)
    allRounds
      .getOrElse(List.empty)
      .flatMap(_.scores)                          /** trasforma la lista di Round in lista di Score */
      .filter(_.user.username == user.username)   /** filtra soo le Score dell'utente in input */
      .filter(_.score != -1)                      /** esclude i valori -1 (round non ancora giocato dall'utente) */
      .foldRight(0)(_.score + _)                  /** calcola il punteggio per accumulazione */
    
  /** ottiene i round giocati per il game aperto in questo momento nella dashboard */
  def getPlayedRounds: Option[List[Round]] =
    GameController.gameOfLoggedUsers
      .flatMap(game =>
        Await
          .result(roundRepository.getAllRoundsByGame(game), 5.seconds)
      )

  /** ottiene tutti i round per il game passato come parametro */
  def getAllRoundByGame(game: Game): List[Round] = {
    Await.result(roundRepository.getAllRoundsByGame(game), 5.seconds).getOrElse(List.empty)
  }

  /** a fine giocata, resetto le variabili per la prossima giocata */
  def resetVariable(): Unit =
    _round = None
    _player = None

end RoundController
