package it.unibo.pps.controller

import it.unibo.pps.business.QuestionRepository
import it.unibo.pps.model.*

import scala.concurrent.Await
import scala.concurrent.duration.*
import scala.util.Random

/** Controller per la gestione delle domande */
object QuestionController:

  private var _question: Option[Question] = None
  private val questionRepository = new QuestionRepository
  var counterQuestionRound: Int = 0
  val QUESTION_FOR_ROUND: Int = 3

  def getQuestion: Option[Question] = _question

  /** Metodo per estrarre una domanda casuale appartenente ad una categoria specifica.
    * @param category
    *   categoria della domanda da estrarre
    * @return
    *   domanda casuale appartenente alla categoria specificata
    */
  private def getRandomQuestionByCategory(category: Category): Question =
    Await
      .result(questionRepository.getQuestionsByCategory(category), 5.seconds)
      .map(questions => questions(Random.nextInt(questions.length)))
      .head

  /** Metodo per gestire il numero di domande da visualizzare all'utente durante un turno.
    *
    * Ad ogni invocazione, si incrementa il contatore delle domande visualizzate durante il turno. Una volta raggiunto
    * il numero massimo di domande da visualizzare per quel turno, il contatore viene resettato.
    *
    * @return
    *   [[true]] se è possibile visualizzare un'altra domanda, [[false]] altrimenti
    */
  def nextQuestion: Boolean =
    counterQuestionRound = (counterQuestionRound + 1) % QUESTION_FOR_ROUND
    counterQuestionRound != 0

  /** Metodo per preparare la domanda da visualizzare all'utente */
  def prepareQuestion(): Unit = {
    var lastRound: Option[Round] = None
    if counterQuestionRound == 0 then lastRound = manageNewRound()
    else lastRound = RoundController.round
    lastRound.foreach(r => {
      GameController.gameOfLoggedUsers
        .map(_.categories(r.numberRound - 1))
        .foreach(category => {
          QuestionController.getRandomQuestionByCategory(category)
          _question = Some(QuestionController.getRandomQuestionByCategory(category))
        })
    })
  }

  /** crea una domanda */
  // TODO: da cancellare
  def createQuestion(question: Question): Unit =
    questionRepository.create(question)

  /** Metodo per gestire la progressione dei round.
    *
    * Innanzitutto ottiene l'ultimo relativo al gioco in corso. Se esso non è presente, significa che la partita è
    * appena iniziata e occorre creare il primo round. Al contrario, se l'ultimo round risulta presente, si verifica se
    * è in corso o completato. Nel primo caso si passa al secondo giocatore, nel secondo si crea il round successivo.
    * @return
    *   round appena iniziato o in corso
    */
  private def manageNewRound(): Option[Round] = {
    val game = GameController.gameOfLoggedUsers

    GameController.getLastRoundByGame
      .map { round =>
        if round.scores.count(_.score == -1) > 0 then
          // Siamo nel mezzo di un round - deve giocare user2
          RoundController.round = round
          round.scores
            .find(_.score == -1)
            .map(_.user)
            .map(nextPlayer => {
              RoundController.player = nextPlayer
              round
            })
        else
          // Devo creare il round successivo - inizia a giocare firstPlayer
          game.map(g => initializeNewRound(g.getID, g.players.head, round.numberRound + 1))
      }
      .getOrElse {
        // Devo creare il primo round - inizia a giocare firstPlayer
        game.map(g => initializeNewRound(g.getID, g.players.head))
      }
  }

  /** Metodo per inizializzare un nuovo round, il quale verrà poi effettivamente creato grazie a
    * [[RoundController.createRound]].
    * @param gameId
    *   ID del gioco per il quale creare il nuovo round
    * @param user
    *   utente che inizierà a giocare il nuovo round
    * @param roundNumber
    *   numero del nuovo round
    * @return
    *   round appena inizializzato
    */
  private def initializeNewRound(gameId: String, user: User, roundNumber: Int = 1): Round = {
    val newScores = GameController.gameOfLoggedUsers.map(_.players.map(new Score(_))).getOrElse(List.empty)
    val newRound = new Round(gameId, newScores, roundNumber)
    RoundController.createRound(newRound)
    RoundController.round = newRound
    RoundController.player = user
    newRound
  }

end QuestionController
