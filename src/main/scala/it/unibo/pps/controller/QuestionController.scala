package it.unibo.pps.controller

import it.unibo.pps.business.QuestionRepository
import it.unibo.pps.model.{Category, Question, Round, User}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Random

object QuestionController:
  
  private var question: Question = null
  private val questionRepository = new QuestionRepository
  
  def getRandomQuestionByCategory(category: Category): Question =
    val questionResult = Await.result(questionRepository.getQuestionsByCategory(category), Duration.Inf)
    val randomQuestion = Random.nextInt(questionResult.length - 1)
    questionResult(randomQuestion)

  def setQuestion(newQuestion: Question): Unit = question = newQuestion
  def getQuestion: Question = question

  def prepareQuestion() : Question = {

    var lastRound: Round = GameController.getLastRoundByGame
    var userPlayer: User = null
    var newRound: Boolean = true
    
    if lastRound == null then
      // Devo creare il primo round - inizia a giocare user1
      userPlayer = GameController.gameOfLoggedUsers.get.getUser1
      lastRound = new Round(GameController.gameOfLoggedUsers.get.getID, -1, -1, 1)
    else if lastRound.getPoint2 == -1 then
      // Siamo nel mezzo di un round - deve giocare user2
      userPlayer = GameController.gameOfLoggedUsers.get.getUser2
      newRound = false
    else
      // Devo creare il game successivo - inizia a giocare user1
      userPlayer = GameController.gameOfLoggedUsers.get.getUser1
      lastRound = new Round(GameController.gameOfLoggedUsers.get.getID, -1, -1, lastRound.getNumberRound + 1)

    // A questo punto ho il round di gioco e l'utente che deve rispondere alle domande
    // Estraggo la categoria del round
    val questionCategory: Category = GameController.gameOfLoggedUsers.get.getCategories(lastRound.getNumberRound - 1)
    val newQuestion: Question = QuestionController.getRandomQuestionByCategory(questionCategory)
    RoundController.setRound(lastRound)
    RoundController.setPlayer(userPlayer)
    setQuestion(newQuestion)

    if newRound then RoundController.createRound(lastRound)
    newQuestion
    
  }
  
end QuestionController
