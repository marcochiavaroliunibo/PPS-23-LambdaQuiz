package it.unibo.pps.controller

import it.unibo.pps.business.QuestionRepository
import it.unibo.pps.model.*

import scala.concurrent.Await
import scala.concurrent.duration.*
import scala.util.Random

/** Controller per la gestione delle domande */
@SuppressWarnings(Array("org.wartremover.warts.Var", "org.wartremover.warts.DefaultArguments"))
object QuestionController:

  private val questionRepository = new QuestionRepository

  /** Domanda corrente da mostrare all'utente */
  private var _question: Option[Question] = None

  /** Contatore delle domande visualizzate durante un turno */
  var counterQuestionRound: Int = 0

  /** Numero di domande da visualizzare all'utente durante un turno */
  val QUESTION_FOR_ROUND: Int = 3

  def getQuestion: Option[Question] = _question

  /** Metodo per estrarre una domanda casuale appartenente ad una categoria specifica.
    * @param category
    *   categoria della domanda da estrarre
    * @return
    *   domanda casuale appartenente alla categoria specificata
    */
  private def getRandomQuestionByCategory(category: Category): Option[Question] =
    try {
      val a = Await.result(questionRepository.getQuestionsByCategory(category), 5.seconds)
      a.map(questions => questions(Random.nextInt(questions.length)))
    } catch {
      case e: Exception =>
        e.printStackTrace()
        None
    }

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
    if counterQuestionRound == 0 then lastRound = RoundController.manageNewRound
    else lastRound = RoundController.round

    lastRound.foreach(r => {
      GameController.gameOfLoggedUsers
        .map(_.categories(r.numberRound - 1))
        .foreach(category => {
          _question = getRandomQuestionByCategory(category)
        })
    })
  }

end QuestionController
