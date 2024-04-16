package it.unibo.pps

import it.unibo.pps.business.{GameRepository, QuestionRepository, RoundRepository, UserRepository}
import it.unibo.pps.controller.CategoryController
import it.unibo.pps.model.*
import it.unibo.pps.model.Category.Storia
import reactivemongo.api.bson.BSONDocument

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TestDataInitializer:
  val gameRepository = new GameRepository
  val userRepository = new UserRepository
  val roundRepository = new RoundRepository
  val questionRepository = new QuestionRepository

  val categories: List[Category] = CategoryController.getRandomCategories(3)
  val players: List[User] = (1 to 2).map(i => User(s"User$i", s"passwd${i + 1}")).toList
  val game: Game = Game(
    players,
    false,
    LocalDateTime.now(),
    categories
  )
  private val scoreList = players.zipWithIndex.map((user, index) => Score(user, index + 1))
  val round: Round = Round(game.id, scoreList, 2)
  val question: Question = Question(
    "Chi è stato il primo presidente degli Stati Uniti?",
    List("Thomas Jefferson", "Abraham Lincoln", "John Adams", "George Washington"),
    3,
    Storia
  )

  def initData: Future[Unit] =
    Future
      .sequence(players.map(userRepository.create))
      .map(_ => {})
      .andThen(_ => gameRepository.create(game))
      .andThen(_ => roundRepository.create(round))
      .andThen(_ => questionRepository.create(question))

  def cleanData(): Future[Unit] =
    val selectAll = BSONDocument()
    userRepository
      .delete(selectAll)
      .andThen(_ => gameRepository.delete(selectAll))
      .andThen(_ => roundRepository.delete(selectAll))
      .andThen(_ => questionRepository.delete(selectAll))
      .andThen(_ => Thread.sleep(10))

end TestDataInitializer
