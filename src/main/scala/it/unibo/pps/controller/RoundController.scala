package it.unibo.pps.controller

import it.unibo.pps.business.RoundRepository
import it.unibo.pps.model.{Game, Round, User}
import it.unibo.pps.view.UIUtils
import org.scalactic.TypeCheckedTripleEquals.convertToCheckingEqualizer
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

import scala.concurrent.Await
import scala.concurrent.duration.*

@SuppressWarnings(Array("org.wartremover.warts.Var", "org.wartremover.warts.DefaultArguments"))
object RoundController:

  private var _round: Option[Round] = None
  private var _player: Option[User] = None
  private val roundRepository = new RoundRepository

  def round_=(newRound: Round): Unit = _round = Some(newRound)
  def round: Option[Round] = _round
  def player_=(newPlayer: User): Unit = _player = Some(newPlayer)
  def player: Option[User] = _player

  /** Metodo per salvare un nuovo round nel database */
  def createRound(round: Round): Unit = roundRepository.create(round)

  /** Metodo invocato dalla View ogni volta che l'utente gioca un round, ovvero risponde a una domanda.
    *
    * In primo luogo, viene controllato se la risposta data dall'utente è corretta o meno, aggiornando il punteggio di
    * conseguenza. Poi, se non ci sono più domande per il round corrente, viene verificato se la partita è terminata e
    * vengono resettate le variabili di stato relative al round.
    *
    * @param answerIndex
    *   l'indice della risposta scelta dall'utente
    * @return
    *   [[true]] se ci sono altre domande per il round corrente, [[false]] altrimenti
    */
  def playRound(answerIndex: Int): Boolean =
    QuestionController.getQuestion.foreach(question => {
      if question.correctAnswer == answerIndex then
        updatePoints(true)
        UIUtils.showSimpleAlert(AlertType.Confirmation, "Risposta corretta!")
      else
        updatePoints(false)
        UIUtils.showSimpleAlert(AlertType.Error, "Risposta sbagliata!")
    })

    val hasNextQuestion = QuestionController.nextQuestion
    if !hasNextQuestion then
      GameController.verifyGameEnd()
      RoundController.resetVariable()
    hasNextQuestion

  /** Metodo per aggiornare i punti dell'utente che ha giocato il round corrente sulla base della risposta data.
    * @param correct
    *   booleano che indica se la risposta data è corretta o meno
    */
  private def updatePoints(correct: Boolean): Unit =
    player.flatMap(p =>
      round.map(r => {
        r.setPoint(p, correct)
        roundRepository.update(r, r.id)
      })
    )

  /** Metodo per calcolare il punteggio parziale di un utente sulla base dei round giocati fino a quel momento.
    * @param user
    *   lo [[User]] di cui si vuole conoscere il punteggio
    * @param game
    *   il [[Game]] a cui si riferiscono i round per il calcolo del punteggio. Se non viene passato, si considera la
    *   partita corrente.
    * @return
    *   il punteggio parziale dell'utente. Se non ci sono round giocati, ritorna [[0]]
    */
  def computePartialPointsOfUser(user: User, game: Option[Game] = None): Int =
    val g = game.orElse(GameController.gameOfLoggedUsers)
    val allRounds = g.flatMap(gm => Await.result(roundRepository.getAllRoundsByGame(gm), 5.seconds))

    allRounds
      .getOrElse(List.empty[Round])
      .flatMap(_.scores)                        // trasforma la lista di Round in lista di Score
      .filter(_.user.username == user.username) // filtra solo le Score dell'utente in input
      .filter(_.score != -1)                    // esclude i valori -1 (round non ancora giocato dall'utente)
      .foldRight(0)(_.score + _)                // calcola il punteggio per accumulazione

  /** Metodo per ottenere i round giocati partendo dalla partita corrente.
    * @return
    *   la lista dei round giocati finora
    */
  def getPlayedRounds: Option[List[Round]] =
    GameController.gameOfLoggedUsers
      .flatMap(game =>
        Await
          .result(roundRepository.getAllRoundsByGame(game), 5.seconds)
      )

  /** Metodo per ottenere tutti i round di una partita.
    * @param game
    *   la partita di cui si vogliono ottenere i round
    * @return
    *   la lista dei round della partita
    */
  def getAllRoundByGame(game: Game): List[Round] = {
    Await.result(roundRepository.getAllRoundsByGame(game), 5.seconds).getOrElse(List.empty)
  }

  /** Metodo invocato alla fine di un round per resettare le variabili di stato */
  def resetVariable(): Unit =
    _round = None
    _player = None

end RoundController
