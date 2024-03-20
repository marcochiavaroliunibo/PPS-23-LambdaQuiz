package it.unibo.pps.controller

import it.unibo.pps.business.QuestionRepository
import it.unibo.pps.model.{Category, Question, Round, User}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Random

object QuestionController:
  
  private var question: Question = null
  private val questionRepository = new QuestionRepository
  private var counterQuestionRound: Int = 0
  private val QUESTION_FOR_ROUND: Int = 3
  
  def getRandomQuestionByCategory(category: Category): Question =
    val questionResult = Await.result(questionRepository.getQuestionsByCategory(category), Duration.Inf).get
    val randomQuestion = Random.nextInt(questionResult.length)
    println(randomQuestion)
    questionResult(randomQuestion)

  def setQuestion(newQuestion: Question): Unit = question = newQuestion
  def getQuestion: Question = question

  /** funzione per gestire il numero di domande da visualizzare all'utente
   * basta modificre la costante per cambiare il numero di domande effettuate ad ogni turno */
  def nextQuestion: Boolean = {
    counterQuestionRound = counterQuestionRound + 1
    if (counterQuestionRound == QUESTION_FOR_ROUND) then false else true
  }

  /** funzione che si occupa di caricare la domanda da visualizzare all'utente */
  /**
  Per recuperare la domanda da fare:
  1) recuperare l'ultimo round
  2a) se non c'è, si inizia da [round 1 - user 1]
  2b) se c'è, vedere se è in corso o completato
    3a) se è in corso, [round x - user 2]
    3b) se è completato, [round x + 1 - user 1]
  */
  def prepareQuestion() : Question = {

    var lastRound: Round = GameController.getLastRoundByGame
    var userPlayer: User = null

    if lastRound == null then
      // Devo creare il primo round - inizia a giocare user1
      userPlayer = GameController.gameOfLoggedUsers.get.getUser1
      lastRound = new Round(GameController.gameOfLoggedUsers.get.getID, -1, -1, 1)
      RoundController.createRound(lastRound)
    else if lastRound.getPoint2 == -1 then
      // Siamo nel mezzo di un round - deve giocare user2
      userPlayer = GameController.gameOfLoggedUsers.get.getUser2
    else
      // Devo creare il game successivo - inizia a giocare user1
      userPlayer = GameController.gameOfLoggedUsers.get.getUser1
      lastRound = new Round(GameController.gameOfLoggedUsers.get.getID, -1, -1, lastRound.getNumberRound + 1)
      RoundController.createRound(lastRound)

    // A questo punto ho il round di gioco e l'utente che deve rispondere alle domande
    // Estraggo la categoria del round
    val questionCategory: Category = GameController.gameOfLoggedUsers.get.getCategories(lastRound.getNumberRound - 1)
    val newQuestion: Question = QuestionController.getRandomQuestionByCategory(questionCategory)
    RoundController.setRound(lastRound)
    RoundController.setPlayer(userPlayer)
    setQuestion(newQuestion)

    newQuestion
    
  }
  
end QuestionController
