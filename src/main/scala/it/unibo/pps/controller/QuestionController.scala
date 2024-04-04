package it.unibo.pps.controller

import it.unibo.pps.business.QuestionRepository
import it.unibo.pps.model.*

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Random

object QuestionController:

  private var _question: Option[Question] = None
  private val questionRepository = new QuestionRepository
  var counterQuestionRound: Int = 0
  val QUESTION_FOR_ROUND: Int = 3

  private def getRandomQuestionByCategory(category: Category): Question =
    val questionResult = Await.result(questionRepository.getQuestionsByCategory(category), Duration.Inf).get
    val randomQuestion = Random.nextInt(questionResult.length)
    questionResult(randomQuestion)

  def getQuestion: Option[Question] = _question

  /** funzione per gestire il numero di domande da visualizzare all'utente basta modificre la costante per cambiare il
    * numero di domande effettuate ad ogni turno
    */
  def nextQuestion: Boolean = {
    counterQuestionRound = counterQuestionRound + 1
    if counterQuestionRound == QUESTION_FOR_ROUND then
      counterQuestionRound = 0
      false
    else true
  }

  /** estraggo la domanda da mostrare */
  def prepareQuestion(): Unit = {
    var lastRound: Option[Round] = None
    if counterQuestionRound == 0 then lastRound = Some(setVariableQuestion())
    else lastRound = RoundController.round

    lastRound.foreach(r => {
      val questionCategory: Category = GameController.gameOfLoggedUsers.get.categories(r.numberRound - 1)
      val newQuestion: Question = QuestionController.getRandomQuestionByCategory(questionCategory)
      _question = Some(newQuestion)
    })
  }

  /** funzione che si occupa di caricare la domanda da visualizzare all'utente */
  /** Per recuperare la domanda da fare: 1) recuperare l'ultimo round 2a) se non c'è, si inizia da [round 1 - user 1]
    * 2b) se c'è, vedere se è in corso o completato 3a) se è in corso, [round x - user 2] 3b) se è completato, [round x
    * + 1 - user 1]
    */
  private def setVariableQuestion(): Round = {
    val game = GameController.gameOfLoggedUsers.get
    val firstPlayer = GameController.gameOfLoggedUsers.map(_.players.head)

    val lastRound = GameController.getLastRoundByGame
      .map { round =>
        if (round.scores.count(_.score == -1) > 0) {
          // Siamo nel mezzo di un round - deve giocare user2
          val userPlayer = round.scores.find(_.score == -1).get.user
          RoundController.round = round
          RoundController.player = userPlayer
          round
        } else {
          // Devo creare il game successivo - inizia a giocare firstPlayer
          this.createRound(game.getID, firstPlayer, round.numberRound + 1)
        }
      }
      .getOrElse {
        // Devo creare il primo round - inizia a giocare firstPlayer
        createRound(game.getID, firstPlayer, 1)
      }

    lastRound
  }
  
  def createQuestion(question: Question) : Unit =
    questionRepository.create(question)

  private def createRound(gameId: String, user: Option[User], roundNumber: Int): Round = {
    val newScores = GameController.gameOfLoggedUsers.map(_.players.map(new Score(_))).getOrElse(List.empty)
    val newRound = new Round(gameId, newScores, roundNumber)
    RoundController.createRound(newRound)
    RoundController.round = newRound
    RoundController.player = user.get
    newRound
  }

end QuestionController
