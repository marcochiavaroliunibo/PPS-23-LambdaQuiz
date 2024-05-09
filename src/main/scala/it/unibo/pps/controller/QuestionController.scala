package it.unibo.pps.controller

import it.unibo.pps.business.QuestionRepository
import it.unibo.pps.model.*

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.*
import scala.util.Random

/** Controller per la gestione delle domande */
@SuppressWarnings(Array("org.wartremover.warts.Var", "org.wartremover.warts.DefaultArguments"))
object QuestionController:

  private val questionRepository = new QuestionRepository

  /** Domanda corrente da mostrare all'utente */
  private var _question: Option[Question] = None
  def getQuestion: Option[Question] = _question

  /** Contatore delle domande visualizzate durante un turno */
  var counterQuestionRound: Int = 0

  /** Numero di domande da visualizzare all'utente durante un turno */
  val QUESTION_FOR_ROUND: Int = 3

  /** lista delle domande di una certa categoria presenti nel database */
  private var questions: Option[List[Question]] = None

  /** lista delle domande già visualizzate, utile per evitare ripetizioni nello stesso round */
  private val extractedQuestions: ListBuffer[Question] = ListBuffer.empty

  /** Metodo per ottenere la prossima domanda da mostrare all'utente, evitando di ripetere quelle già visualizzate.
    *
    * @param category
    *   categoria della domanda da estrarre
    * @return
    *   domanda casuale appartenente alla categoria specificata
    */
  private def getNextQuestion(category: Category): Option[Question] =
    questions
      .orElse {
        questions = Await.result(questionRepository.getQuestionsByCategory(category), 5.seconds)
        questions
      }
      .map(_.filterNot(extractedQuestions.contains))
      .map { qstns =>
        val question = qstns(Random.nextInt(qstns.size))
        extractedQuestions += question
        question
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
    val areOtherQuestions = counterQuestionRound != 0
    if !areOtherQuestions then extractedQuestions.clear()
    areOtherQuestions

  /** Metodo per preparare la domanda da visualizzare all'utente */
  def prepareQuestion(): Unit = {
    var lastRound: Option[Round] = None
    if counterQuestionRound == 0 then lastRound = RoundController.manageNewRound
    else lastRound = RoundController.round

    lastRound.foreach(r => {
      GameController.gameOfLoggedUsers
        .map(_.categories(r.numberRound - 1))
        .foreach(category => {
          _question = getNextQuestion(category)
        })
    })
  }

  /** Metodo per resettare la lista delle domande già visualizzate */
  def resetQuestions(): Unit = {
    extractedQuestions.clear()
    questions = None
  }

end QuestionController
